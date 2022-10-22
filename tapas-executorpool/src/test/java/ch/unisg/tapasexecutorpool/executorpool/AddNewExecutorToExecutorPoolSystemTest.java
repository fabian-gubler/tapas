package ch.unisg.tapasexecutorpool.executorpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.BDDAssertions.*;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import ch.unisg.tapasexecutorpool.executorpool.application.port.out.AddExecutorPort;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor.ExecutorType;

import ch.unisg.tapasexecutorpool.executorpool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.ExecutorRepository;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

/**
 * AddNewExecutorToExecutorPoolSystemTest
 * GROUP1:
 * /home/fabian/tapas_g1/executor-pool/src/test/java/ch/unisg/executorpool/executorpool/AddNewExecutorToExecutorPoolSystemTest.java
 * /home/fabian/tapas_g1/roster/src/test/java/ch/unisg/roster/roster/AddNewAssignmentToRosterServiceSystemTest.java
 * GROUP2:
 * /home/fabian/tapas_g2/tapas-roster/src/test/java/ch/unisg/tapasroster/roster/AddExecutorToExecutorPoolSystemTest.java
 * /home/fabian/tapas_g2/tapas-roster/src/test/java/ch/unisg/tapasroster/roster/AssignTaskToExecutorSystemTest.java
 * RONNY:
 * /home/fabian/tapas_up/tapas-tasks/src/test/java/ch/unisg/tapastasks/tasks/AddNewTaskToTaskListSystemTest.java
 */
public class AddNewExecutorToExecutorPoolSystemTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AddExecutorPort addExecutorPort;

	@Autowired
	private Environment environment;

	@Test
	void addNewExecutorToExecutorPoolSystemTest() throws JSONException {

		Executor.ExecutorType executorType = new Executor.ExecutorType("Joke");
		Executor.Endpoint executorEndpoint = new Executor.Endpoint("Endpoint");

		// Executor executor = Executor.createExecutorWithTypeAndEnpoint(new
		// Executor.ExecutorType(executorType),
		// new Executor.Endpoint(executorEndpoint));

		ResponseEntity response = whenAddNewExecutorToEmptyPool(executorType, executorEndpoint);

		URI location = response.getHeaders().getLocation();

		then(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

	}

	private ResponseEntity whenAddNewExecutorToEmptyPool(ExecutorType executorType,
			Executor.Endpoint executorEndpoint) throws JSONException {
		ExecutorPool.getExecutorPool().getListOfExecutors().getValue().clear();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", ExecutorJsonRepresentation.MEDIA_TYPE);

		String jsonPayLoad = new JSONObject()
				.put("executorType", executorType.getValue())
				.put("executorEndpoint", executorEndpoint.getValue())
				.toString();

		HttpEntity<String> request = new HttpEntity<>(jsonPayLoad, headers);

		return restTemplate.exchange("/executor-pool/executors", HttpMethod.POST, request, Object.class);
	}

}
