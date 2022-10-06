package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEventHandler;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundError;
import org.springframework.stereotype.Component;

@Component
public class TaskStartedHandler implements TaskStartedEventHandler {

    @Override
    public boolean handleTaskStarted(TaskStartedEvent taskStartedEvent)  {
        TaskList taskList = TaskList.getTapasTaskList();
        try {
            return taskList.changeTaskStatusToRunning(taskStartedEvent.getTaskId());
        } catch (TaskNotFoundError e) {
            throw new RuntimeException(e);
        }
    }
}
