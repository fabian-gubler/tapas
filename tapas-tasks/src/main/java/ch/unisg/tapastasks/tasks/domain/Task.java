package ch.unisg.tapastasks.tasks.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity**/
public class Task {
    public enum Status {
        OPEN, ASSIGNED, RUNNING, EXECUTED
    }

    @Getter
    private final TaskId taskId;

    @Getter
    private final TaskName taskName;

    @Getter
    private final TaskType taskType;

    @Getter @Setter
    private TaskStatus taskStatus;

    @Getter @Setter
    private InputData inputData;

    @Getter @Setter
    private OutputData outputData;

    private Task(TaskName taskName, TaskType taskType, InputData input) {
        this.taskId = new TaskId(UUID.randomUUID().toString());
        this.taskName = taskName;
        this.taskType = taskType;
        this.inputData = input;
        this.taskStatus = new TaskStatus(Status.OPEN);
    }

    //Constructor from repo
    public Task(TaskId taskId, TaskName taskName, TaskType taskType, InputData input, OutputData output,
                TaskStatus taskStatus) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
        this.inputData = input;
        this.outputData = output;
        this.taskStatus = taskStatus;
    }


    public static Task createTaskWithNameAndType(TaskName name, TaskType type) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Task: " + name.getValue() + " " + type.getValue());
        return new Task(name, type, null);
    }

    public static Task createTaskWithNameAndTypeAndInput(TaskName name, TaskType type, InputData input) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Task: " + name.getValue() + " " + type.getValue());
        return new Task(name, type, input);
    }

    //This is for recreating a task from a repository.
    public static Task createwithIdNameTypeInputOutputStatus(TaskId taskId, TaskName taskName, TaskType taskType,
                                                             InputData input, OutputData output,
                                                             TaskStatus taskStatus) {
        return new Task(taskId, taskName, taskType, input, output, taskStatus);
    }


    @Value
    public static class TaskId {
        String value;
    }

    @Value
    public static class TaskName {
        String value;
    }

    @Value
    public static class TaskType {
        String value;
    }

    @Value
    public static class TaskStatus {
        Status value;
    }

    @Value
    public static class InputData {
        String value;
    }

    @Value
    public static class OutputData {
        String value;
    }
}
