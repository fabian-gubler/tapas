package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ch.unisg.tapasexecutorpool.executorpool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.executorpool.adapter.in.messaging.UnknownExecutorException;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RemoveExecutorFromExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RemoveExecutorFromExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import lombok.RequiredArgsConstructor;

/**
 * Controller that handles HTTP requests for removing an executor from the executor pool. This controller implements the
 * {@link RemoveExecutorFromExecutorPoolUseCase} use case using the {@link RemoveExecutorFromExecutorPoolCommand}.
 *
 * The provided executor is removed from the executor pool
 *
 * If the request is successful, the controller returns an HTTP 200 OK status
 */

@RestController
@RequiredArgsConstructor
public class RemoveExecutorFromExecutorPoolWebController {

    //Instead of explicitly adding an ingoing port here, we are directly referencing the use case to reduce redundancy.
    private final RemoveExecutorFromExecutorPoolUseCase removeExecutorFromExecutorPoolUseCase;

    // Used to retrieve properties from application.properties
    @Autowired
    private Environment environment;

    /**
     * remove existing executor from executorPool by executorID, which is mapped from JSON to an executor object.
     *
     * @param executorId
     * @return
     */
    @DeleteMapping(path = "/executors/{executorId}", consumes = {ExecutorJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity<Void> addNewExecutorToExecutorPool(@PathVariable("executorId") String executorId) {
        try {
            RemoveExecutorFromExecutorPoolCommand command = new RemoveExecutorFromExecutorPoolCommand(new Executor.ExecutorId(executorId));

            Boolean isRemoved = removeExecutorFromExecutorPoolUseCase.removeExecutorFromExecutorPool(command);

            if (!isRemoved) {
                throw new UnknownExecutorException();
            }

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UnknownExecutorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Executor could not be found in executor pool");
        }
    }

}
