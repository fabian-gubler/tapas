package ch.unisg.tapasroster.roster.adapter.in.web;

import ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb.RosterAssignmentRepository;
import ch.unisg.tapasroster.roster.application.port.in.MatchExecutorToReceivedTaskCommand;
import ch.unisg.tapasroster.roster.application.port.in.MatchExecutorToReceivedTaskUseCase;
import ch.unisg.tapasroster.roster.domain.Executor;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
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
    private RosterAssignmentRepository executorRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    public void matchExecutorToReceivedTask() throws Exception {

        String taskLocation = "TEST_LOCATION";
        String taskType = "apiTEST";
        String taskList = "TEST_TASKLIST";
        String inputData = "TEST_INPUTDATA";
        String originalTaskUri = "ORIGINAL_TASK_URI";

        String jsonPayLoad = new JSONObject()
            .put("taskLocation", taskLocation)
            .put("taskType", taskType)
            .put("taskList", taskList)
            .put("inputData", inputData)
            .put("originalTaskUri", originalTaskUri)
            .toString();

        Executor executorStub = Executor.createExecutorWithTypeAndEnpoint(new Executor.ExecutorType(taskType), new Executor.Endpoint("/executor/api"));

        MatchExecutorToReceivedTaskCommand command = new MatchExecutorToReceivedTaskCommand(taskType,taskLocation,inputData, originalTaskUri);

        // fixme: test does not work anymore, as the match executor is now an async function and does not return the found executor directly
        // Mockito.when(matchExecutorToReceivedTaskUseCase.matchExecutorToReceivedTask(command)).thenReturn(new Roster.ExecutorEndpoint(executorStub.getEndpoint().getValue()));

        mvc.perform(post("/roster/newtask/")
                .contentType("application/json")
                .content(jsonPayLoad))
                .andExpect(status().isOk()
        );

        then(matchExecutorToReceivedTaskUseCase).should()
            .matchExecutorToReceivedTask(eq(new MatchExecutorToReceivedTaskCommand(
                taskType,
                taskLocation,
                inputData,
                originalTaskUri
            )));
    }

}
