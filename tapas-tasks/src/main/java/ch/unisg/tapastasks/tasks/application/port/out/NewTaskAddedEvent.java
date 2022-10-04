package ch.unisg.tapastasks.tasks.application.port.out;

public class NewTaskAddedEvent {
    public String taskId;
    public String taskListName;

    public String taskType;

    public String inputData;

    public NewTaskAddedEvent(String taskId, String taskListName, String taskType, String inputData) {
        this.taskId = taskId;
        this.taskListName = taskListName;
        this.taskType = taskType;
        this.inputData = inputData;
    }
}

