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
        //Here we would need to work with DTOs in case the payload of calls becomes more complex

        var values = new HashMap<String, String>() {{
            put("taskLocation", event.taskLocation);
            put("taskType", event.taskType);
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
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
