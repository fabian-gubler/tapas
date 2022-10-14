package ch.unisg.tapasexecutorpool.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ch.unisg.tapasexecutorpool.executorpool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.executorpool.adapter.in.web.AddNewExecutorToExecutorPoolWebController;
import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.ExecutorRepository;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;

@WebMvcTest(controllers = AddNewExecutorToExecutorPoolWebController.class)
public class AddNewExecutorToExecutorPoolWebControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AddNewExecutorToExecutorPoolUseCase addNewExecutorToExecutorPoolUseCase;

	@MockBean
	ExecutorRepository executorRepository;

	@Test
	public void testAddNewExecutorToExecutorPool() throws Exception {

		String executorType = "JOKE";

		String jsonPayLoad = new JSONObject()
			.put("executorType", executorType)
			.toString();

		// ExecutorClass executorStub = ExecutorClass.createExecutorClass(new
		// ExecutorUri(java.net.URI.create(executorUri)),
		// new ExecutorClass.ExecutorTaskType(executorTaskType));

		// Executor executorStud = Executor.createExecutorWithTypeAndEnpoint(new )

        // Executor executorStub = Executor.createExecutorWithNameUriType(new Executor.ExecutorName(executorName),
        //         new Executor.ExecutorUri(executorUri), new Task.TaskType(taskType));

		// AddNewExecutorToExecutorPoolCommand addNewExecutorToExecutorPoolCommand = new
		// AddNewExecutorToExecutorPoolCommand(
		// new ExecutorUri(java.net.URI.create(executorUri)), new
		// ExecutorTaskType(executorTaskType));

		// Mockito.when(addNewExecutorToExecutorPoolUseCase
		// .addNewExecutorToExecutorPool(addNewExecutorToExecutorPoolCommand))
		// .thenReturn(executorStub);


		// FROM SAMPLE CONFIG

        // Task taskStub = Task.createTaskWithNameAndType(new Task.TaskName(taskName),
        //     new Task.TaskType(taskType));
        //
        // AddNewTaskToTaskListCommand addNewTaskToTaskListCommand = new AddNewTaskToTaskListCommand(
        //     new Task.TaskName(taskName), new Task.TaskType(taskType),
        //     Optional.empty()
        // );
        //
        // Mockito.when(addNewTaskToTaskListUseCase.addNewTaskToTaskList(addNewTaskToTaskListCommand))
        //     .thenReturn(taskStub.getTaskId().getValue());

		mvc.perform(post("/executors/")
		.contentType(ExecutorJsonRepresentation.MEDIA_TYPE)
		.content(jsonPayLoad))
		.andExpect(status().isCreated());
		
		// then(addNewExecutorToExecutorPoolUseCase).should()
		// .addNewExecutorToExecutorPool(eq(new AddNewExecutorToExecutorPoolCommand(
		// new ExecutorUri(java.net.URI.create(executorUri)), new
		// ExecutorTaskType(executorTaskType))));
	}

}
