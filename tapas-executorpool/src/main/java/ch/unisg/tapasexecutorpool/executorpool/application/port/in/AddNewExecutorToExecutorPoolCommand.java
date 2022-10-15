package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * Command class which initialise a new executor.
 */

@Value
@EqualsAndHashCode(callSuper=false)
public class AddNewExecutorToExecutorPoolCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    private final Executor.Endpoint endpoint;

    @NotNull
    private final Executor.ExecutorType executorType;


    /**
     * constructor to create new Executor.
     * @param endpoint
     * @param executorType
     */
    public AddNewExecutorToExecutorPoolCommand(Executor.Endpoint endpoint, Executor.ExecutorType executorType) {
        this.executorType = executorType;
        this.endpoint = endpoint;

        this.validateSelf();
    }
}
