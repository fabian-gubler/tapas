package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveExecutorFromExecutorpoolQuery;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.executorpool.adapter.in.messaging.UnknownExecutorException;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RetrieveExecutorFromExecutorpoolUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;

@RestController
@RequiredArgsConstructor
public class RetrieveExecutorByTypeWebController {
    private final RetrieveExecutorFromExecutorpoolUseCase retrieveExecutorFromExecutorpoolUseCase;

    // Used to retrieve properties from application.properties
    @Autowired
    private Environment environment;

    /**
     *
     * @param executorType
     * @return
     */
    @GetMapping(path = "/executors/{type}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveExecutorByType(@PathVariable("type") String executorType) {
        try {
            RetrieveExecutorFromExecutorpoolQuery query = new RetrieveExecutorFromExecutorpoolQuery(new Executor.ExecutorType(executorType.toUpperCase()));

            // todo: at the moment only a single executor is returned, if multiple executors of the same type are added
            // there will still be only 1 executor returned, refactor to return List<Executor> as JSON
            Executor executorList = retrieveExecutorFromExecutorpoolUseCase.retrieveExecutorFromExecutorPool(query);

            if (executorList == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matching executor could not be found in executor pool");
            }

            String executorRepresentation = ExecutorJsonRepresentation.serialize(executorList);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, ExecutorJsonRepresentation.MEDIA_TYPE);
            return new ResponseEntity<>(executorRepresentation, responseHeaders, HttpStatus.OK);
        } catch (ConstraintViolationException | JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UnknownExecutorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Executor could not be found in executor pool");
        }
    }
}
