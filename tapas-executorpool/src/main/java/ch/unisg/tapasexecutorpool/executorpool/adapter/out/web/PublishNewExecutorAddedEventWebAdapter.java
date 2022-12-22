package ch.unisg.tapasexecutorpool.executorpool.adapter.out.web;

import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewExecutorAddedEvent;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewExecutorAddedEventPort;
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
public class PublishNewExecutorAddedEventWebAdapter implements NewExecutorAddedEventPort {

    @Autowired
    private Environment environment;

    @Override
    public void publishNewExecutorAddedEvent(NewExecutorAddedEvent event) {

        String server = environment.getProperty("auction-house.baseuri");

        System.out.println(server + "executors/" + event.executorType + "/" + event.executorType);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(server + "executors/" + event.executorType + "/" + event.executorType))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(""))
            .build();

        System.out.println("Published new executor to auction house: " + request);
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
