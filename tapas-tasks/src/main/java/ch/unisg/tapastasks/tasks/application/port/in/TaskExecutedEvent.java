package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task.*;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Value
public class TaskExecutedEvent extends SelfValidating<TaskExecutedEvent> {
    @NotNull
    private final TaskId taskId;

    @Getter
    private final Optional<OutputData> outputData;

    public TaskExecutedEvent(TaskId taskId, Optional<OutputData> outputData) {
        this.taskId = taskId;
        this.outputData = outputData;

        this.validateSelf();
    }
}
