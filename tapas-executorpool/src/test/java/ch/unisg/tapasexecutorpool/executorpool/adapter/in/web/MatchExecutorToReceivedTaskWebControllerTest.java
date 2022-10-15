package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.ExecutorRepository;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.MatchExecutorToReceivedTaskCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.MatchExecutorToReceivedTaskUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MatchExecutorToReceivedTaskWebController.class)
class MatchExecutorToReceivedTaskWebControllerTest {

    @MockBean
    private MatchExecutorToReceivedTaskUseCase matchExecutorToReceivedTaskUseCase;

    @MockBean
    private ExecutorRepository executorRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    public void matchExecutorToReceivedTask() throws Exception {

        String taskLocation = "TEST_LOCATION";
        String taskType = "JOKETEST";
        String taskList = "TEST_TASKLIST";
        String inputData = "TEST_INPUTDATA";

        String jsonPayLoad = new JSONObject()
            .put("taskLocation", taskLocation)
            .put("taskType", taskType)
            .put("taskList", taskList)
            .put("inputData", inputData)
            .toString();

        Executor executorStub = Executor.createExecutorWithTypeAndEnpoint(new Executor.ExecutorType(taskType), new Executor.Endpoint("/executor/joke"));

        MatchExecutorToReceivedTaskCommand command = new MatchExecutorToReceivedTaskCommand(taskType,taskLocation,inputData);

        Mockito.when(matchExecutorToReceivedTaskUseCase.matchExecutorToReceivedTask(command)).thenReturn(executorStub);

        mvc.perform(post("/roster/newtask/")
                .contentType("application/json")
                .content(jsonPayLoad))
                .andExpect(status().isOk()
        );

        then(matchExecutorToReceivedTaskUseCase).should()
            .matchExecutorToReceivedTask(eq(new MatchExecutorToReceivedTaskCommand(
                taskType,
                taskLocation,
                inputData
            )));
    }

}
