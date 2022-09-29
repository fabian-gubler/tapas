package ch.unisg.tapastasks.tasks.application.port.in;

public interface TaskExecutedEventHandler  {

    boolean handleTaskExecuted(TaskExecutedEvent taskExecutedEvent);
}
