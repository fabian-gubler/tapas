package ch.unisg.tapastasks.tasks.application.port.in;

import ch.unisg.tapastasks.tasks.domain.Task;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class UpdateTaskCommand {

    @NotNull
    @Getter
    private final Task.TaskId taskId;


    @Getter
    private final Optional<Task.TaskStatus> status;

    @Getter
    private final Optional<Task.OutputData> taskOutput;


    public UpdateTaskCommand(Task.TaskId taskId, Optional<Task.TaskStatus> status, Optional<Task.OutputData> taskOutput) {
        this.taskId = taskId;
        this.status = status;
        this.taskOutput = taskOutput;
    }
}
