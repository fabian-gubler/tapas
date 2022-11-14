package ch.unisg.tapastasks.tasks.application.handler;

import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskAssignedEventHandler;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundError;
import org.springframework.stereotype.Component;

@Component
public class TaskAssignedHandler implements TaskAssignedEventHandler {

    @Override
    public boolean handleTaskAssigned(TaskAssignedEvent taskAssignedEvent) throws TaskNotFoundError {
        TaskList taskList = TaskList.getTapasTaskList();
        return taskList.changeTaskStatusToAssigned(taskAssignedEvent.getTaskId(),
            taskAssignedEvent.getServiceProvider());
    }
}
