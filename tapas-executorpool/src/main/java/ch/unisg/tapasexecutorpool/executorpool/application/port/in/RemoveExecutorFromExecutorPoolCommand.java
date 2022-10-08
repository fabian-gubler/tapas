package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * This class implements the logic to remove the Executor from executorPool by the executorID and validates it.
 */
@Value
public class RemoveExecutorFromExecutorPoolCommand extends SelfValidating<RemoveExecutorFromExecutorPoolCommand> {

    @NotNull
    private final Executor.ExecutorId executorId;

    public  RemoveExecutorFromExecutorPoolCommand(Executor.ExecutorId executorId) {
        this.executorId = executorId;
        this.validateSelf();
    }
}
