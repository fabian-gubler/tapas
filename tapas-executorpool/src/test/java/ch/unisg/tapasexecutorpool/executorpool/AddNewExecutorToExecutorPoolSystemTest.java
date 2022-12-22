package ch.unisg.tapasexecutorpool.executorpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddNewExecutorToExecutorPoolSystemTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AddExecutorPort addExecutorPort;

	@Autowired
	private Environment environment;

	@Test
	void addNewExecutorToExecutorPoolSystemTest() throws JSONException {

		Executor.ExecutorType executorType = new Executor.ExecutorType("executor-miro");
		Executor.Endpoint executorEndpoint = new Executor.Endpoint("Endpoint");

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

		return restTemplate.exchange("/executors/", HttpMethod.POST, request, Object.class);
	}
}
