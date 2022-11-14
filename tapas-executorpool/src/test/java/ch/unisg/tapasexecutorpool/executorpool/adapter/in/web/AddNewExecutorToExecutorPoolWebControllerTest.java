package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.then;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ch.unisg.tapasexecutorpool.executorpool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.ExecutorRepository;

@WebMvcTest(controllers = AddNewExecutorToExecutorPoolWebController.class)
public class AddNewExecutorToExecutorPoolWebControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AddNewExecutorToExecutorPoolUseCase addNewExecutorToExecutorPoolUseCase;

	@MockBean
	private ExecutorRepository executorRepository;

	@Test
	public void addNewExecutorToExecutorPool() throws Exception {

		String executorType = "API";
		String endpoint = "http://executor-api:8083/executor/api";

		String jsonPayLoad = new JSONObject()
			.put("executorType", executorType)
			.put("endpoint", endpoint)
			.toString();

		// ERROR: This must return object of String type
        Executor executorStub = Executor.createExecutorWithTypeAndEnpoint(new Executor.ExecutorType(executorType),
                new Executor.Endpoint(endpoint));

        AddNewExecutorToExecutorPoolCommand command = new AddNewExecutorToExecutorPoolCommand(
                new Executor.Endpoint(endpoint), new Executor.ExecutorType(executorType));

		// ERROR: Here executorStub must be the argument of .thenReturn
        Mockito.when(addNewExecutorToExecutorPoolUseCase.addNewExecutorToExecutorPool(command)).thenReturn("");
        // Mockito.when(addNewExecutorToExecutorPoolUseCase.addNewExecutorToExecutorPool(command)).thenReturn(executorStub);

		mvc.perform(post("/executors/")
		.contentType(ExecutorJsonRepresentation.MEDIA_TYPE)
		.content(jsonPayLoad))
		.andExpect(status().isCreated());

		then(addNewExecutorToExecutorPoolUseCase).should()
			.addNewExecutorToExecutorPool(eq(new AddNewExecutorToExecutorPoolCommand(
			new Executor.Endpoint(endpoint),
			new Executor.ExecutorType(executorType)
			)));
	}

}
