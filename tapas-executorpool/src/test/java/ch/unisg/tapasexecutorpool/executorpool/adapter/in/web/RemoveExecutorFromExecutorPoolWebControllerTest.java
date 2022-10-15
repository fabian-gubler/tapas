package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import ch.unisg.tapasexecutorpool.executorpool.adapter.in.formats.ExecutorJsonRepresentation;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor.ExecutorId;

// @WebMvcTest(controllers = RemoveExecutorFromExecutorPoolWebController.class)
@AutoConfigureMockMvc
public class RemoveExecutorFromExecutorPoolWebControllerTest {
/*
	@Autowired
	private MockMvc mvc;

	@Test
	public void removeExecutorFromExecutorPool() throws Exception {
		ExecutorId executorId = new ExecutorId("7a6814c7-b0ef-4e71-9019-8961d90bef19");

		String jsonPayLoad = new JSONObject()
				// .put("executorId", executorId)
				.toString();

		mvc.perform(delete("/executors/{executorId}")
				.contentType(ExecutorJsonRepresentation.MEDIA_TYPE)
				.content(jsonPayLoad))
				.andExpect(status().isCreated());
	}*/
}
