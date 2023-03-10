package ch.unisg.tapas.auctionhouse.adapter.out.messaging.http;

import ch.unisg.tapas.auctionhouse.application.port.out.auctions.AuctionWonEventPort;
import ch.unisg.tapas.auctionhouse.domain.Auction;
import ch.unisg.tapas.auctionhouse.domain.AuctionWonEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is a template for an HTTP adapter that sends auction won events. This class was created
 * here only as a placeholder, it is up to you to decide how such events should be sent.
 */
@Component
// profile is not used as both mqtt and websub use http outg adapter to send the shadow task
// @Profile("http-websub")
public class AuctionWonEventHttpAdapter implements AuctionWonEventPort {
    private static final Logger LOGGER = LogManager.getLogger(AuctionWonEventHttpAdapter.class);

    @Autowired
    private Environment environment;

    @Override
    public void publishAuctionWonEvent(AuctionWonEvent event, Auction.AuctionedTaskUri auctionedTaskUri) {
        try {
            LOGGER.info("publishing shadow Task to winner: " + event.getWinningBid().getBidderName());
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestTask = HttpRequest.newBuilder()
                .uri(auctionedTaskUri.getValue())
                .header("content-type", "application/task+json")
                .GET()
                .build();
            HttpResponse<String> retrievedTask = client.send(requestTask, HttpResponse.BodyHandlers.ofString());

            JSONObject shadowTaskJson = new JSONObject(retrievedTask.body());
            shadowTaskJson.remove("taskStatus");
            shadowTaskJson.remove("taskListUri");
            shadowTaskJson.remove("outputData");
            shadowTaskJson.put("originalTaskUri",environment.getProperty("tasks.list.uri") + "/tasks/" + shadowTaskJson.get("taskId"));

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(event.getWinningBid().getBidderTaskListUri().getValue() + "tasks/"))
                .header("content-type", "application/task+json")
                .POST(HttpRequest.BodyPublishers.ofString(shadowTaskJson.toString()))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
