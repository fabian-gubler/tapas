package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

import java.util.Optional;

public interface MatchExecutorToReceivedTaskUseCase {
    Optional<Executor> matchExecutorToReceivedTask(MatchExecutorToReceivedTaskCommand command);
}
