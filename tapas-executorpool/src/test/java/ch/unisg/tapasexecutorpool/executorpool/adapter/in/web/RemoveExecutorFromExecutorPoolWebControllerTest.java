package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

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
