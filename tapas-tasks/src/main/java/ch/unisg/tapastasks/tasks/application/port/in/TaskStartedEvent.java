package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.common.SelfValidating;
import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class TaskStartedEvent extends SelfValidating<TaskStartedEvent> {
    @NotNull
    private final Task.TaskId taskId;

    public TaskStartedEvent(Task.TaskId taskId) {
        this.taskId = taskId;
        this.validateSelf();
    }



}
