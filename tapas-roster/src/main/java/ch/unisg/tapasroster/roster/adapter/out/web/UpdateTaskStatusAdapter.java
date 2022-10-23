package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.out.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Component
@Primary
public class UpdateTaskStatusAdapter implements UpdateTaskStatusUseCase {

    @Autowired
    private Environment environment;

    @Override
    public Boolean updateTaskStatusUseCase(UpdateTaskStatusCommand command) {
        var values = new HashMap<String, String>() {{
            put("taskStatus", command.taskStatus.toString());
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
            .uri(URI.create(String.valueOf(command.taskLocation)))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
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
            System.out.println("Executor endpoint not available, setting status to pending");
        }
        return false;
    }
}
