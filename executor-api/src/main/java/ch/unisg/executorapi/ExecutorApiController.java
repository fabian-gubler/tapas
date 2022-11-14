package ch.unisg.executorapi;

import ch.unisg.executorapi.formats.TaskInputRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExecutorApiController {

    private final ExecuteTaskService executeTaskService;

    @PostMapping(path = "/executor/api")
    private ResponseEntity<String> getData(@RequestBody TaskInputRepresentation payload, @RequestHeader(HttpHeaders.LOCATION) String taskLocation) {
        try {
            executeTaskService.execute(payload.getInputData(), taskLocation);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
