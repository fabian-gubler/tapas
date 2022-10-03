package ch.unisg.tapastasks.tasks.application.port.out;

public class NewTaskAddedEvent {
    public String taskId;
    public String taskListName;

    public String taskType;

    public NewTaskAddedEvent(String taskId, String taskListName, String taskType) {
        this.taskId = taskId;
        this.taskListName = taskListName;
        this.taskType = taskType;
    }
}

