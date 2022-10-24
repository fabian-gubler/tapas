package ch.unisg.tapasexecutorpool.executorpool.application.port.in;


/**
 * Interface which informs the command class which return type and parameter is required to use.
 */
public interface RemoveExecutorFromExecutorPoolUseCase {
    Boolean removeExecutorFromExecutorPool(RemoveExecutorFromExecutorPoolCommand command);
}
