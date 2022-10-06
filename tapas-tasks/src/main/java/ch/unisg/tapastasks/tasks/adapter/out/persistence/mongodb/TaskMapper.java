package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import org.springframework.stereotype.Component;

@Component
class TaskMapper {

    Task mapToDomainEntity(MongoTaskDocument task) {
        return Task.createwithIdNameTypeInputOutputStatus(
            new Task.TaskId(task.taskId),
            new Task.TaskName(task.taskName),
            new Task.TaskType(task.taskType),
            new Task.InputData(task.inputData),
            new Task.OutputData(task.outputData),
            new Task.TaskStatus(Task.Status.valueOf(task.taskStatus))
        );
    }

    MongoTaskDocument mapToMongoDocument(Task task) {

        String taskInputData =
            (task.getInputData() == null) ? ""
                : task.getInputData().getValue();

        String taskOutputData =
            (task.getOutputData() == null) ? ""
                : task.getOutputData().getValue();

        return new MongoTaskDocument(
            task.getTaskId().getValue(),
            task.getTaskName().getValue(),
            task.getTaskType().getValue(),
            taskInputData,
            taskOutputData,
            task.getTaskStatus().getValue().toString(),
            TaskList.getTapasTaskList().getTaskListName().getValue()
        );
    }
}
