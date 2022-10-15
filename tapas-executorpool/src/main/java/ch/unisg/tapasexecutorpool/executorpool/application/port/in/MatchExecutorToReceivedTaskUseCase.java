package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;


/**
 * Interface which informs the command class which return type and parameter is required to use.
 */
public interface MatchExecutorToReceivedTaskUseCase {
    Executor matchExecutorToReceivedTask(MatchExecutorToReceivedTaskCommand command);
}
