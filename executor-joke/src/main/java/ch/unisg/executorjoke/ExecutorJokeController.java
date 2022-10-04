package ch.unisg.executorjoke;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExecutorJokeController {

    private final String API_URI = "https://api.chucknorris.io/jokes/random";

    @PostMapping(path = "/executor/joke")
    private ResponseEntity<String> getJoke() {
        RestTemplate restTemplate = new RestTemplate();
        return new ResponseEntity<>(restTemplate.getForObject(API_URI, String.class), HttpStatus.OK);
    }
}
