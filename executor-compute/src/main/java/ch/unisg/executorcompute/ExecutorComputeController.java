package ch.unisg.executorcompute;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.unisg.executorcompute.adapters.ComputationJsonRepresentation;

@RestController
@RequiredArgsConstructor

public class ExecutorComputeController {

    private final ExecuteComputationService executeComputationService;

    @PostMapping(path = "/executor/compute/{computationType}")
    public ResponseEntity<String> triggerComputation(@PathVariable("computationType") String computationType, @RequestBody ComputationJsonRepresentation payload, @RequestHeader(HttpHeaders.LOCATION) String returnLocation) {
        System.out.println("Computing task: " + returnLocation);
        executeComputationService.compute(computationType, payload, returnLocation);
        return new ResponseEntity<>("Computation triggered", HttpStatus.OK);
    }


}
