package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.out.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@Primary
public class UpdateTaskStatusAdapter implements UpdateTaskStatusUseCase {

    @Autowired
    private Environment environment;

    @Override
    public Boolean updateTaskStatusUseCase(UpdateTaskStatusCommand command) {
        String requestBody = "";
        try {

            JSONObject statusPatch = new JSONObject()
                .put("op", "replace")
                .put("path", "/taskStatus")
                .put("value", command.taskStatus);

            requestBody = new JSONArray().put(statusPatch).toString();


        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(command.taskLocation.getValue()))
            .header("content-type", "application/json-patch+json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))
            .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Status updated for task in tasklist, new Status: "+ command.taskStatus.toString() +" " + response.body());
                return true;
            } else {
                // todo: save status in roster so task status can be updated again if tasklist is unavailable
                System.out.println("Request error, status was not updated: " + response.body());
                System.out.println(response.toString());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Tasklist endpoint not available, setting status to pending");
        }
        return false;
    }
}
