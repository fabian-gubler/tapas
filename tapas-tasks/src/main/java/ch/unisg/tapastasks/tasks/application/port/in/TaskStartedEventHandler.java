package ch.unisg.tapastasks.tasks.application.port.in;

public interface TaskStartedEventHandler {

    boolean handleTaskStarted(TaskStartedEvent taskStartedEvent);
}
