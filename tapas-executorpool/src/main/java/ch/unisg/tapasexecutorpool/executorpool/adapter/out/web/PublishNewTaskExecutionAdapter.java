package ch.unisg.tapasexecutorpool.executorpool.adapter.out.web;

import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewTaskExecutionEvent;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewTaskExecutionEventPort;
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
public class PublishNewTaskExecutionAdapter implements NewTaskExecutionEventPort {

    @Autowired
    private Environment environment;

    @Override
    public void publishNewTaskExecutionEvent(NewTaskExecutionEvent event) {
        var values = new HashMap<String, String>() {{
            put("taskLocation", event.taskLocation);
            put("taskType", event.taskType);
            put("inputData", event.inputData);
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
            .uri(URI.create(event.taskExecutionURI))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Execution successful, response from executor: " + response.body());
            } else {
                System.out.println("Request error: " + response.body());
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
