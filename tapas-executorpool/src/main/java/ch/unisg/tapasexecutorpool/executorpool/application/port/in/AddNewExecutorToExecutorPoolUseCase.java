package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

/**
 * interface which informs the command class which return type and parameter is required to use.
 */
public interface AddNewExecutorToExecutorPoolUseCase {
    String addNewExecutorToExecutorPool(AddNewExecutorToExecutorPoolCommand command);
}
