package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class AddNewExecutorToExecutorPoolCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    private final Executor.Endpoint endpoint;

    @NotNull
    private final Executor.ExecutorType executorType;


    public AddNewExecutorToExecutorPoolCommand(Executor.Endpoint endpoint, Executor.ExecutorType executorType) {
        this.executorType = executorType;
        this.endpoint = endpoint;

        this.validateSelf();
    }
}
