package ch.unisg.tapasexecutorpool.executorpool.adapter.in.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.ExecutorMapper;
import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.ExecutorPersistenceAdapter;
import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.ExecutorRepository;
import ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb.MongoExecutorDocument;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RemoveExecutorFromExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ch.unisg.tapasexecutorpool.executorpool.adapter.in.formats.ExecutorJsonRepresentation;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = RemoveExecutorFromExecutorPoolWebController.class)
public class RemoveExecutorFromExecutorPoolWebControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RemoveExecutorFromExecutorPoolUseCase removeExecutorFromExecutorPoolUseCase;

    @MockBean
    private AddNewExecutorToExecutorPoolUseCase addNewExecutorToExecutorPoolUseCase;

    @MockBean
    private ExecutorRepository executorRepository;


    @Test
    public void removeExecutorFromExecutorPool() throws Exception {

        String executorType = "executor-api";
        String endpoint = "http://executor-api:8083/executor/api";

        Executor executor = Executor.createExecutorWithTypeAndEnpoint(new Executor.ExecutorType(executorType),
            new Executor.Endpoint(endpoint));

        ExecutorMapper executorMapper = new ExecutorMapper();
        MongoExecutorDocument mongoExecutorDocument = executorMapper.mapToMongoDocument(executor);
        executorRepository.save(mongoExecutorDocument);

        mvc.perform(MockMvcRequestBuilders.delete("/executors/{id}", executor.getExecutorId().getValue())
                .contentType(ExecutorJsonRepresentation.MEDIA_TYPE))
            .andExpect(status().is2xxSuccessful());
    }
}
