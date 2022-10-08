package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.AddNewExecutorToExecutorPoolUseCase;
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

    /**
     * method to add a new executor to the executorPool by command, which uses the endpoint and type of the created executor.
     * @param command
     * @return - returns the ID of the executor as a String
     */
    @Override
    public String addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command) {
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();
        Executor newExecutor;

        newExecutor = executorPool.addNewExecutorToExecutorPoolWithTypeAndEndpoint(command.getEndpoint(), command.getExecutorType());

        return newExecutor.getExecutorId().getValue();
    }
}
