package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor.ExecutorType;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class RetrieveExecutorFromExecutorpoolQuery extends SelfValidating {
    @NotNull
    private final ExecutorType executorType;

    public RetrieveExecutorFromExecutorpoolQuery(ExecutorType executorType) {
        this.executorType = executorType;
        this.validateSelf();
    }
}
