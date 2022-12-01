package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapasroster.roster.adapter.in.formats.NewTaskJsonRepresentation;
import ch.unisg.tapasroster.roster.adapter.in.messaging.NoMatchingExecutorException;
import ch.unisg.tapasroster.roster.application.port.in.MatchExecutorToReceivedTaskCommand;
import ch.unisg.tapasroster.roster.application.port.in.MatchExecutorToReceivedTaskUseCase;
import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

/**
 * Controller that handles HTTP requests for matching Executor to a task. This controller implements the
 * {@link MatchExecutorToReceivedTaskUseCase} use case using the {@link MatchExecutorToReceivedTaskCommand}.
 */
@RestController
@RequiredArgsConstructor
public class MatchExecutorToReceivedTaskWebController {
    //Instead of explicitly adding an ingoing port here, we are directly referencing the use case to reduce redundancy.
    private final MatchExecutorToReceivedTaskUseCase matchExecutorToReceivedTaskUseCase;

    // Used to retrieve properties from application.properties
    @Autowired
    private Environment environment;

    // received task are matched to appropriate executors by getting JSON data and map it as a task object.
    @PostMapping(path = "/roster/newtask/", consumes = "application/json")
    public ResponseEntity<RosterAssignment.ExecutorEndpoint> matchExecutorToReceivedTask(@RequestBody NewTaskJsonRepresentation payload) {
        try {
            String taskType = payload.getTaskType();
            String taskLocation = payload.getTaskLocation();
            String inputData = payload.getInputData();
            String originalTaskUri = payload.getOriginalTaskUri();

            System.out.println("Received new task from tasklist, trying to match executor. taskType: " +
                taskType + ", inputData: " + inputData);

            MatchExecutorToReceivedTaskCommand command = new MatchExecutorToReceivedTaskCommand(taskType, taskLocation, inputData, originalTaskUri);

            matchExecutorToReceivedTaskUseCase.matchExecutorToReceivedTask(command);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NoMatchingExecutorException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No suitable executor was found!");
        }
    }
}
