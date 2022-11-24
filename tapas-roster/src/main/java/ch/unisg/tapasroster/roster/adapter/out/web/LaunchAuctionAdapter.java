package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.out.LaunchAuctionCommand;
import ch.unisg.tapasroster.roster.application.port.out.LaunchAuctionUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Component
public class LaunchAuctionAdapter implements LaunchAuctionUseCase {

    @Autowired
    private Environment environment;

    @Override
    public Boolean launchAuction(LaunchAuctionCommand command) {
        var values = new HashMap<String, String>() {{
            put("taskUri", command.getTaskUri());
            put("taskType", command.getTaskType());
            put("deadline", command.getDeadline().toString());
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(environment.getProperty("auction-house.baseuri") + "auctions/"))
            .header("content-type", LaunchAuctionCommand.MEDIA_TYPE)
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println("Auction launched at auction-house: " + response.headers());
                return true;
            } else {
                System.out.println("Auction could not be launched: ");
                System.out.println(response);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Auction-house endpoint not available");
        }
        return false;
    }
}
