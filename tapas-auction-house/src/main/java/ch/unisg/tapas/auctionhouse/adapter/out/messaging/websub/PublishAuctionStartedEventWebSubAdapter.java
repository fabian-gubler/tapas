package ch.unisg.tapas.auctionhouse.adapter.out.messaging.websub;

import ch.unisg.tapas.auctionhouse.application.port.out.auctions.AuctionStartedEventPort;
import ch.unisg.tapas.auctionhouse.domain.AuctionStartedEvent;
import ch.unisg.tapas.common.ConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
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

/**
 * This class is a template for notifying a WebSub Hub that a new auction was started.
 */
@Component
@Profile("http-websub")
public class PublishAuctionStartedEventWebSubAdapter implements AuctionStartedEventPort {
    private static final Logger LOGGER = LogManager.getLogger(PublishAuctionStartedEventWebSubAdapter.class);

    // You can use this object to retrieve properties from application.properties, e.g. the
    // WebSub hub publish endpoint, etc.
    @Autowired
    private ConfigProperties config;

    @Autowired
    private Environment environment;

    @Override
    public void publishAuctionStartedEvent(AuctionStartedEvent event) {
        LOGGER.info("Publishing auction started event via WebSub");

        Map<String, String> map = new HashMap<>();
        map.put("hub.url", environment.getProperty("auction.house.uri")+ "auctions/");
        map.put("hub.mode", "publish");

        String requestBody = map.keySet().stream()
            .map(key -> key + "=" + URLEncoder.encode(map.get(key), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(environment.getProperty("websub.hub")))
            .header("content-type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info("AuctionStarted Event published " + response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
