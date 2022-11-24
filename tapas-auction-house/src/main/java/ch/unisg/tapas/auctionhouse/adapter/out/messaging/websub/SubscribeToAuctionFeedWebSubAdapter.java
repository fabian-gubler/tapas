package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.port.out.feeds.SubscribeToAuctionFeedPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Profile("http-websub")
public class SubscribeToAuctionFeedWebSubAdapter implements SubscribeToAuctionFeedPort {
    private static final Logger LOGGER = LogManager.getLogger(SubscribeToAuctionFeedWebSubAdapter.class);

    @Autowired
    private Environment environment;

    @Override
    public void subscribeToFeed(Auction.AuctionFeedId feedId) {
        LOGGER.info("Subscribing to auction feed via WebSub: " + feedId.getValue());

        String HUB_URI = "";
        String TOPIC ="";

        // 1. Send a request to retrieve the auction feed in order to discover the WebSub hub to subscribe to.
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(feedId.getValue()))
            .header("Content-Type", "application/json")
            .GET()
            .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                for (String link : response.headers().allValues("link")) {
                    if (link.contains("rel=\"hub\"")) {
                        String hub = link.split("href=")[1];
                        hub = hub.split("/>")[0].replace("\"","");
                        HUB_URI = hub;
                    }
                    if (link.contains("rel=\"self\"")) {
                        LOGGER.info(link);
                        String hub = link.split("href=")[1];
                        hub = hub.split("/>")[0].replace("\"","");
                        LOGGER.info(hub);
                        TOPIC = hub;
                        LOGGER.info("Discovered Topic: " +TOPIC);
                    }
                }
            } else {
                LOGGER.error("NO WEBSUB HUB FOUND");
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        // 2. Send a subscription request to the discovered WebSub hub to subscribe to events relevant
        // for this auction house.
        Map<String, String> map = new HashMap<>();
        map.put("hub.callback", environment.getProperty("auction.house.uri") + "auctions/notifications/");
        map.put("hub.mode", "subscribe");
        map.put("hub.topic", TOPIC);

        String requestBody = map.keySet().stream()
            .map(key -> key + "=" + URLEncoder.encode(map.get(key), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));

        HttpRequest subscribeRequest = HttpRequest.newBuilder()
            .uri(URI.create(HUB_URI))
            .header("content-type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        try {
           HttpResponse hubResponse = client.send(subscribeRequest, HttpResponse.BodyHandlers.ofString());
           if(hubResponse.statusCode() == 202) {
               LOGGER.info("Subscription Request sent successfully: " + hubResponse);
           } else {
               LOGGER.info("There was an issue with the subscription request" + hubResponse);
           }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 3. [DONE] Handle the validation of intent from the WebSub hub in (cf. WebSub protocol).
        // This step is already implemented by the REST controller defined in:
        // ch.unisg.tapas.auctionhouse.adapter.in.messaging.websub.WebSubIntentVerificationListener
        //
        // Once the subscription is activated, the hub will send "fat pings" with content updates.
        // The content received from the hub will depend primarily on the design of the Auction House
        // HTTP API.
        //
        // For further details see:
        // - W3C WebSub Recommendation: https://www.w3.org/TR/websub/
        // - the implementation notes of the WebSub hub you are using to distribute events
    }

}
