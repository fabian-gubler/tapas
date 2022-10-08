package ch.unisg.tapasexecutorpool.executorpool.application.port.in;


import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

import java.util.Optional;

/**
 * Interface which informs the command class which return type and parameter is required to use.
 */
public interface RemoveExecutorFromExecutorPoolUseCase {
    Boolean removeExecutorFromExecutorPool(RemoveExecutorFromExecutorPoolCommand command);
}
