package ch.unisg.tapasexecutorpool.executorpool.application.port.in;

import ch.unisg.tapasexecutorpool.common.SelfValidating;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class MatchExecutorToReceivedTaskCommand extends SelfValidating<AddNewExecutorToExecutorPoolCommand> {
    @NotNull
    private final String taskType;

    @NotNull
    private final String taskLocation;

    public MatchExecutorToReceivedTaskCommand(String taskType, String taskLocation) {
        this.taskType = taskType;
        this.taskLocation = taskLocation;

        this.validateSelf();
    }
}
