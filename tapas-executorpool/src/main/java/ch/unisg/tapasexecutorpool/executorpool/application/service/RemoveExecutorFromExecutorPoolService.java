package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RemoveExecutorFromExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RemoveExecutorFromExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import org.springframework.stereotype.Service;

/**
 * Service to remove an executor from the executorPool.
 */
@Service("RemoveExecutorFromExecutorPool")
public class RemoveExecutorFromExecutorPoolService implements RemoveExecutorFromExecutorPoolUseCase {

    /**
     * Removes the executor from the executor pool by ID of the executor.
     * @param command -
     * @return - true, if the executor is removed from the pool
     */
    @Override
    public Boolean removeExecutorFromExecutorPool(RemoveExecutorFromExecutorPoolCommand command) {
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();

        return executorPool.removeExecutorFromExecutorPoolById(command.getExecutorId());
    }
}

