package ch.unisg.tapastasks.tasks.adapter.in.web;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonRepresentation;
import ch.unisg.tapastasks.tasks.application.port.in.UpdateTaskCommand;
import ch.unisg.tapastasks.tasks.application.port.in.UpdateTaskUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UpdateTaskWebController {
    //Instead of explicitly adding an ingoing port here, we are directly referencing the use case to reduce redundancy.
    private final UpdateTaskUseCase updateTaskUseCase;

    // Used to retrieve properties from application.properties
    //@Autowired
    //private Environment environment;

    @PutMapping(path = "/tasks/{taskId}", consumes = "application/json")
    public ResponseEntity<Void> updateTask(@RequestBody TaskJsonRepresentation payload, @PathVariable("taskId") String taskId) {
        try {

            // When creating a task, the task's representation may include optional input data
            Optional<Task.OutputData> taskoutputData =
                (payload.getOutputData() == null) ? Optional.empty()
                    : Optional.of(new Task.OutputData(payload.getOutputData()));

            // todo: implement check to only allow String values represented in enum, otherwise 500 error is thrown
            Optional<Task.TaskStatus> taskStatus =
                (payload.getTaskStatus() == null) ? Optional.empty()
                    : Optional.of(new Task.TaskStatus( Task.Status.valueOf(payload.getTaskStatus())));

            Task.TaskId id = new Task.TaskId(taskId);

            UpdateTaskCommand updateTaskCommand = new UpdateTaskCommand(id, taskStatus, taskoutputData);

            Boolean taskUpdated = updateTaskUseCase.updateTask(updateTaskCommand);

            if (!taskUpdated) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            return new ResponseEntity<Void>(
                HttpStatus.NO_CONTENT);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
