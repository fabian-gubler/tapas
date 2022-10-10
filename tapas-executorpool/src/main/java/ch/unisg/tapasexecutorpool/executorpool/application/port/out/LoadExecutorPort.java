package ch.unisg.tapasexecutorpool.executorpool.application.port.out;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorNotFoundError;

public interface LoadExecutorPort {

    Executor loadExecutor(Executor.ExecutorId executorId) throws ExecutorNotFoundError;

    Executor loadExecutorByType(Executor.ExecutorType executorType) throws ExecutorNotFoundError;
}
