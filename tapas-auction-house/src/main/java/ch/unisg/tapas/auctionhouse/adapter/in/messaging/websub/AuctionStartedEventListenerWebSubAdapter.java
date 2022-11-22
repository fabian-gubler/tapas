package ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub;

import ch.unisg.tapas.auctionhouse.adapter.common.formats.AuctionJsonRepresentation;
import ch.unisg.tapas.auctionhouse.application.handler.AuctionStartedHandler;
import ch.unisg.tapas.auctionhouse.application.port.in.auctions.AuctionStartedEvent;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 * This class is a template for handling auction started events received via WebSub
 */
@RestController
@Profile("http-websub")
@RequiredArgsConstructor
public class AuctionStartedEventListenerWebSubAdapter {
    private static final Logger LOGGER = LogManager.getLogger(AuctionStartedEventListenerWebSubAdapter.class);
    private final AuctionStartedHandler auctionStartedHandler;

    // TODO: handle WebSub notifications for auction started events
    @PostMapping(path = "/auctions/notifications/", consumes = "application/json")
    public ResponseEntity<Void> auctionStarted(@RequestBody String payload) throws URISyntaxException {
        LOGGER.info("received new auction" + payload);

        try {

            Auction auction = AuctionJsonRepresentation.deserialize(payload);
            AuctionStartedEvent auctionStartedEvent = new AuctionStartedEvent(auction);
            auctionStartedHandler.handleAuctionStartedEvent(auctionStartedEvent);

        } catch (JsonProcessingException e){
            LOGGER.error("input not valid: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
