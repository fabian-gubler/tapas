package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEventHandler;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TaskStartedHandler implements TaskStartedEventHandler {

    @Override
    public boolean handleTaskStarted(TaskStartedEvent taskStartedEvent) throws TaskNotFoundException {
        TaskList taskList = TaskList.getTapasTaskList();
        return taskList.changeTaskStatusToRunning(taskStartedEvent.getTaskId());
    }
}
