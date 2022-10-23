package ch.unisg.tapasroster.roster.adapter.out.web;

import ch.unisg.tapasroster.roster.application.port.out.RetrieveExecutorQuery;
import ch.unisg.tapasroster.roster.application.port.out.RetrieveExecutorUseCase;
import ch.unisg.tapasroster.roster.domain.Executor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Component
@Primary
@RestController
public class RetrieveExecutorAdapter implements RetrieveExecutorUseCase {

    @Autowired
    private Environment environment;

    @Override
    @ResponseBody
    public Executor.Endpoint retrieveExecutorUseCase(RetrieveExecutorQuery query) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(environment.getProperty("executorpool.baseuri") + "/executors/" + query.taskType))
            .header("content-type", "application/json")
            .GET()
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Executor found, response from executorpool: " + response.body());

                ObjectMapper objectMapper = new ObjectMapper();
                Map<?, ?> map = objectMapper.readValue(response.body(), Map.class);

                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    if (entry.getKey() == "endpoint") {
                        return new Executor.Endpoint(entry.getValue().toString());
                    }
                }

                return null;
            } else {
                System.out.println("Request error: " + response.body());
                System.out.println(response.toString());
            }
        } catch (
            IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
