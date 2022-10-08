package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * Controller that handles HTTP requests for creating new Executor. This controller implements the
 * {@link AddNewExecutorToExecutorPoolUseCase} use case using the {@link AddNewExecutorToExecutorPoolCommand}.
 *
 * A new executor is created via an HTTP POST request to the /executors/ endpoint.
 *
 * If the request is successful, the controller returns an HTTP 201 Created status code a Location header field that
 * points to the URI of the added executor.
 */

@RestController
@RequiredArgsConstructor
public class AddNewExecutorToExecutorPoolWebController {

    //Instead of explicitly adding an ingoing port here, we are directly referencing the use case to reduce redundancy.
    private final AddNewExecutorToExecutorPoolUseCase addNewExecutorToExecutorPoolUseCase;

    // Used to retrieve properties from application.properties
    @Autowired
    private Environment environment;

    /**
     * Takes the executor type out of the JSON payload and converts it to an executor object.
     * After this convertion you can handle the payload as a normal executor object and process it.
     *
     * @param payload - the inserted data from the user of the application.
     * @return - an http status if the mapping has worked.
     */
    @PostMapping(path = "/executors/", consumes = {ExecutorJsonRepresentation.MEDIA_TYPE})
    public ResponseEntity<Void> addNewExecutorToExecutorPool(@RequestBody ExecutorJsonRepresentation payload) {
        try {
            Executor.ExecutorType executorType = new Executor.ExecutorType(payload.getExecutorType());
            Executor.Endpoint endpoint = new Executor.Endpoint(payload.getEndpoint());

            AddNewExecutorToExecutorPoolCommand command = new AddNewExecutorToExecutorPoolCommand(endpoint, executorType);

            String executorId = addNewExecutorToExecutorPoolUseCase.addNewExecutorToExecutorPool(command);

            HttpHeaders responseHeaders = new HttpHeaders();
            // Construct and advertise the URI of the newly created task; we retrieve the base URI
            // from the application.properties file
            responseHeaders.add(HttpHeaders.LOCATION, environment.getProperty("baseuri")
                + "executors/" + executorId);

            return new ResponseEntity<Void>(responseHeaders,
                HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
