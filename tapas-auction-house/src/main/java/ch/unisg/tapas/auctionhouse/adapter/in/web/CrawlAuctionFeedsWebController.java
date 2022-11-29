package ch.unisg.tapas.auctionhouse.adapter.in.web;

import ch.unisg.tapas.auctionhouse.application.port.in.feeds.SubscribeToAuctionFeedCommand;
import ch.unisg.tapas.auctionhouse.application.port.in.feeds.SubscribeToAuctionFeedUseCase;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequiredArgsConstructor
public class CrawlAuctionFeedsWebController {

    private static final Logger LOGGER = LogManager.getLogger(CrawlAuctionFeedsWebController.class);

    private final SubscribeToAuctionFeedUseCase subscribeToAuctionFeedUseCase;

    @Autowired
    private Environment environment;


    @PostMapping(path = "/crawlAuctionFeeds/")
    public ResponseEntity<Void> initiateCrawling(@RequestBody(required = false) String payload) {
        LOGGER.info("Crawling location: " + payload);

        String resourceLocation = payload != null ? payload : environment.getProperty("group1.auction-feed");
        crawlResource(resourceLocation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Very basic crawler, this works for now but should be improved.
     * @param location
     */
    private void crawlResource(String location) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(location))
            .header("Content-Type", "application/json")
            .GET()
            .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                for (String link : response.headers().allValues("link")) {
                    if (link.contains("rel=\"entertainment\"") || link.contains("rel=\"computation\"")) {
                        LOGGER.info("Business client found: " + link);
                        String newLocation = link.split(">")[0].substring(1);
                        SubscribeToAuctionFeedCommand command = new SubscribeToAuctionFeedCommand(new Auction.AuctionFeedId(newLocation));
                        subscribeToAuctionFeedUseCase.subscribeToFeed(command);
                    }
                    else if (link.contains("rel=\"experimental\"")) {
                        LOGGER.info("Crawling new resource " + link.split(">")[0].substring(1));
                        crawlResource(link.split(">")[0].substring(1));
                    }
                }
            }

        } catch (IOException e) {
            LOGGER.error("IOException: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
