package ch.unisg.executorjoke;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

@Service
public class ExecuteTaskService {

    private final String API_URI = "https://api.genderize.io/?name=";

    @Async
    public void execute(String inputData, String taskLocation) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String apiResponse = restTemplate.getForObject(API_URI + "inputData", String.class);
            Thread.sleep(5000);
            sendResponse(true, apiResponse, taskLocation);
        } catch (Exception e) {
            sendResponse(false, "", taskLocation);
        }
    }

    private void sendResponse(Boolean isSuccessful, String outputData, String uri) {
        try {
            var values = new HashMap<String, String>();

            values.put("success", isSuccessful.toString());
            values.put("outputData", outputData);

            var objectMapper = new ObjectMapper();
            String requestBody = null;
            try {
                requestBody = objectMapper.writeValueAsString(values);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("SENT OUTPUTDATA TO TASK WITH LOCATION: " + uri
                + "OUTPUTDATA IS: " + outputData);
            if (response.statusCode() == 200) {
                System.out.println("OutputData updated for task in tasklist");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("task finished request failed!");
            throw new RuntimeException(e);
        }
    }
}
