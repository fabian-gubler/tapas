package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListCommand;
import ch.unisg.tapastasks.tasks.application.port.in.AddNewTaskToTaskListUseCase;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEvent;
import ch.unisg.tapastasks.tasks.application.port.out.NewTaskAddedEventPort;
import ch.unisg.tapastasks.tasks.domain.Task;

import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
@Service("AddNewTaskToTaskList")
public class AddNewTaskToTaskListService implements AddNewTaskToTaskListUseCase {

    private final NewTaskAddedEventPort newTaskAddedEventPort;

    @Override
    public String addNewTaskToTaskList(AddNewTaskToTaskListCommand command) {
        TaskList taskList = TaskList.getTapasTaskList();
        Task newTask;

        // The buckpal project has some stubs for locking of the Account at this point. This would be the responsibility
        // of the service that implements the use case. For our implementation of the task list there is no immediate
        // reason to lock the list during the adding of a new task.

        if (command.getTaskInput().isPresent()) {
            newTask = taskList.addNewTaskWithNameAndTypeAndInput(command.getTaskName(), command.getTaskType(),
               command.getTaskInput().get());
        } else {
            newTask = taskList.addNewTaskWithNameAndType(command.getTaskName(), command.getTaskType());
        }

        //Here we are using the application service to emit an event to the outside of the bounded context.
        //This event should be considered as a light-weight "integration event" to communicate side effects to other services.

        if (newTask != null) {
            NewTaskAddedEvent newTaskAdded = new NewTaskAddedEvent(newTask.getTaskName().getValue(),
                    taskList.getTaskListName().getValue(), newTask.getTaskType().getValue(), newTask.getInputData().getValue());
            newTaskAddedEventPort.publishNewTaskAddedEvent(newTaskAdded);
        }

        return newTask.getTaskId().getValue();
    }
}
