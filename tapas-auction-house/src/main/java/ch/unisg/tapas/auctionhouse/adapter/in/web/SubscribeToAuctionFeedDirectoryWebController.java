package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.application.port.in.feeds.SubscribeToAuctionFeedDirectoryCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.feeds.SubscribeToAuctionFeedDirectoryUseCase;
import ch.unisg.tapas.common.ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Controller that handles HTTP requests for subscribing to auction feeds from an Auction House Resource
 * Directory. This controller implements the {@link SubscribeToAuctionFeedDirectoryUseCase} use case
 * using the {@link SubscribeToAuctionFeedDirectoryCommand} command.
 */
@RestController
@Profile("websub")
@RequiredArgsConstructor
public class SubscribeToAuctionFeedDirectoryWebController {
    private static final Logger LOGGER = LogManager.getLogger(SubscribeToAuctionFeedDirectoryWebController.class);

    private final SubscribeToAuctionFeedDirectoryUseCase subscribeToAuctionFeedDirectoryUseCase;

    @Autowired
    private ConfigProperties config;

    /**
     * Handles an HTTP POST request for subscribing to auction feeds listed in an Auction House
     * Resource Directory.
     *
     * @param payload the URI of the Auction House Resource Directory
     * @return a 200 OK status code if the subscription is successful, an error status code otherwise
     */
    @PostMapping(path = "/discovery/directory/")
    public ResponseEntity<Void> launchAuction(@RequestBody(required = false) String payload) {
        URI directoryUri = URI.create(config.getPropertyByName("websub.discovery.directory"));

        try {
             directoryUri = URI.create(payload);
        } catch (IllegalArgumentException e) {
            LOGGER.info("The Auction House Directory URI is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            LOGGER.info("No URI was given for the Auction House Directory");
        } finally {
            LOGGER.info("Using the default directory URI: " + directoryUri.toASCIIString());
        }

        SubscribeToAuctionFeedDirectoryCommand command = new SubscribeToAuctionFeedDirectoryCommand(directoryUri);
        subscribeToAuctionFeedDirectoryUseCase.subscribeToDirectory(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
