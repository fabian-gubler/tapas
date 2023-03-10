package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.AddExecutorPort;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewExecutorAddedEvent;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewExecutorAddedEventPort;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

/**
 * class of service to add a new Executor to the executorPool.
 */
@RequiredArgsConstructor
@Component
@Transactional
@Service("AddNewExecutorToExecutorPool")
public class AddNewExecutorToExecutorPoolService implements AddNewExecutorToExecutorPoolUseCase {

    private final AddExecutorPort addExecutorToRepositoryPort;

    private final NewExecutorAddedEventPort newExecutorAddedEventPort;

    /**
     * method to add a new executor to the executorPool by command, which uses the endpoint and type of the created executor.
     * @param command
     * @return - returns the ID of the executor as a String
     */
    @Override
    public String addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command) {
        Executor newExecutor = Executor.createExecutorWithTypeAndEnpoint(command.getExecutorType(),command.getEndpoint());

        addExecutorToRepositoryPort.addExecutor(newExecutor);

        ExecutorPool executorPool = ExecutorPool.getExecutorPool();

        executorPool.addNewExecutorToExecutorPoolWithTypeAndEndpoint(command.getEndpoint(), command.getExecutorType());

        // Publish the new executor to the auction house
        NewExecutorAddedEvent event = new NewExecutorAddedEvent(command.getEndpoint().getValue(), command.getExecutorType().getValue());
        newExecutorAddedEventPort.publishNewExecutorAddedEvent(event);

        return newExecutor.getExecutorId().getValue();
    }
}
