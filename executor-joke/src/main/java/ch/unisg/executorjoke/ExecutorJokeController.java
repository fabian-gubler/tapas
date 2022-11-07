package ch.unisg.executorjoke;

import ch.unisg.executorjoke.formats.TaskInputRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class ExecutorJokeController {

    private final ExecuteTaskService executeTaskService;

    @PostMapping(path = "/executor/joke")
    private ResponseEntity<String> getJoke(@RequestBody TaskInputRepresentation payload, @RequestHeader(HttpHeaders.LOCATION) String taskLocation) {
        try {
            executeTaskService.execute(payload.getInputData(), taskLocation);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
