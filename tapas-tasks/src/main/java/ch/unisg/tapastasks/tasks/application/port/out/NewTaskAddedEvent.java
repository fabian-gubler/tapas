package ch.unisg.tapastasks.tasks.application.port.out;

public class NewTaskAddedEvent {
    public String taskId;
    public String taskListName;

    public String taskType;

    public String inputData;

    public String taskListUri;

    public String originalTaskUri;

    public NewTaskAddedEvent(String taskId, String taskListName, String taskType, String taskListUri) {
        this.taskId = taskId;
        this.taskListName = taskListName;
        this.taskType = taskType;
        this.inputData = "";
        this.taskListUri = taskListUri;

    }

    public NewTaskAddedEvent(String taskId, String taskListName, String taskType, String inputData, String taskListUri, String originalTaskUri) {
        this.taskId = taskId;
        this.taskListName = taskListName;
        this.taskType = taskType;
        this.inputData = inputData;
        this.taskListUri = taskListUri;
        this.originalTaskUri = originalTaskUri;
    }
}

