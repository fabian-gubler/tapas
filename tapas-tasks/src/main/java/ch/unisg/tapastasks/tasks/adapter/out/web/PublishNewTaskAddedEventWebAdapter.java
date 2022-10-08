package ch.unisg.tapastasks.tasks.adapter.out.web;

import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEvent;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Component
@Primary
public class PublishNewTaskAddedEventWebAdapter implements NewTaskAddedEventPort {

    @Autowired
    private Environment environment;

    @Override
    public void publishNewTaskAddedEvent(NewTaskAddedEvent event) {

        //Here we would need to work with DTOs in case the payload of calls becomes more complex

        String server = environment.getProperty("executorpool.baseuri");

        var values = new HashMap<String, String>() {{
            put("taskLocation", environment.getProperty("baseuri") + event.taskId);
            put("taskType", event.taskType);
            put("tasklist", event.taskListName);
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
            .uri(URI.create(server + "/roster/newtask/"))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        System.out.println("Published new task to roster: " + request);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
