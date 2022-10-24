package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class RetrieveExecutorFromExecutorpoolQuery extends SelfValidating {
    @NotNull
    private final Executor.ExecutorType executorType;

    public RetrieveExecutorFromExecutorpoolQuery(Executor.ExecutorType executorType) {
        this.executorType = executorType;
        this.validateSelf();
    }
}
