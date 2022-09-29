package ch.unisg.tapastasks.tasks.application.port.out;

public class NewTaskAddedEvent {
    public String taskId;
    public String taskListName;

    public NewTaskAddedEvent(String taskId, String taskListName) {
        this.taskId = taskId;
        this.taskListName = taskListName;
    }
}

