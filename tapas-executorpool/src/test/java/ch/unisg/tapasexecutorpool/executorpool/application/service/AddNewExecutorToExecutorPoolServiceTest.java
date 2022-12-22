package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.AddExecutorPort;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewExecutorAddedEvent;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewExecutorAddedEventPort;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class AddNewExecutorToExecutorPoolServiceTest {
    private final AddExecutorPort addExecutorPort = Mockito.mock(AddExecutorPort.class);
    // private final TaskListLock taskListLock = Mockito.mock(TaskListLock.class);

    private final NewExecutorAddedEventPort newExecutorAddedEventPort = Mockito.mock(NewExecutorAddedEventPort.class);
    private final AddNewExecutorToExecutorPoolService addNewExecutorToExecutorPoolService = new AddNewExecutorToExecutorPoolService(
        addExecutorPort, newExecutorAddedEventPort);

    @Test
    void addingSucceeds() {

        Executor newExecutor = givenAnExecutorWithTypeAndEndpoint(new Executor.ExecutorType("executor-compute"),
            new Executor.Endpoint("http://localhost:8089/compute/"));

        ExecutorPool executorPool = givenAnEmptyExecutorPool(ExecutorPool.getExecutorPool());

        AddNewExecutorToExecutorPoolCommand addNewExecutorToExecutorPoolCommand = new AddNewExecutorToExecutorPoolCommand(newExecutor.getEndpoint(),
            newExecutor.getExecutorType());

        String addedExecutorId = addNewExecutorToExecutorPoolService.addNewExecutorToExecutorPool(addNewExecutorToExecutorPoolCommand);

        assertThat(addedExecutorId).isNotNull();
        assertThat(executorPool.getListOfExecutors().getValue()).hasSize(1);

        then(newExecutorAddedEventPort).should(times(1))
           .publishNewExecutorAddedEvent(any(NewExecutorAddedEvent.class));
    }

    private ExecutorPool givenAnEmptyExecutorPool(ExecutorPool executorPool) {
        executorPool.getListOfExecutors().getValue().clear();
        return executorPool;
    }

    private Executor givenAnExecutorWithTypeAndEndpoint(Executor.ExecutorType executorType, Executor.Endpoint endpoint) {
        Executor executor = Mockito.mock(Executor.class);
        given(executor.getEndpoint()).willReturn(endpoint);
        given(executor.getExecutorType()).willReturn(executorType);
        return executor;
    }
}
