package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RemoveExecutorFromExecutorPoolCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.RemoveExecutorFromExecutorPoolUseCase;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import org.springframework.stereotype.Service;

@Service("RemoveExecutorFromExecutorPool")
public class RemoveExecutorFromExecutorPoolService implements RemoveExecutorFromExecutorPoolUseCase {

    @Override
    public Boolean removeExecutorFromExecutorPool(RemoveExecutorFromExecutorPoolCommand command) {
        ExecutorPool executorPool = ExecutorPool.getExecutorPool();

        return executorPool.removeExecutorFromExecutorPoolById(command.getExecutorId());
    }
}

