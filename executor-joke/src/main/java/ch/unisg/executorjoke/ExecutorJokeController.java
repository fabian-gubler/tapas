package ch.unisg.executorjoke;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExecutorJokeController {

    private final String API_URI = "https://api.chucknorris.io/jokes/random";

    @GetMapping("/executor/joke")
    public String index() {
        return getJoke();
    }

    private String getJoke() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(API_URI, String.class);
    }
}
