package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.port.in.UpdateTaskCommand;
import ch.unisg.tapastasks.tasks.application.port.in.UpdateTaskUseCase;
import ch.unisg.tapastasks.tasks.application.port.out.LoadTaskPort;
import ch.unisg.tapastasks.tasks.application.port.out.UpdateTaskPort;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import ch.unisg.tapastasks.tasks.domain.TaskNotFoundError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;
@RequiredArgsConstructor
@Component
@Transactional
@Service("UpdateTask")
public class UpdateTaskService implements UpdateTaskUseCase {

    private final UpdateTaskPort updateTaskPort;
    @Override
    public Boolean updateTask(UpdateTaskCommand command) {
        TaskList taskList = TaskList.getTapasTaskList();

        if (command.getStatus().isPresent()) {
            System.out.println("Updating task, new Status: " + command.getStatus().get().getValue());
        }
        if (command.getTaskOutput().isPresent()) {
            System.out.println("Updating task, new OutputData: " + command.getTaskOutput().get().getValue());
        }

        try {
            Task task = taskList.retrieveTaskById(command.getTaskId());

            if (command.getStatus().isPresent()) {
                task.setTaskStatus(command.getStatus().get());
            }

            if (command.getTaskOutput().isPresent()) {
                task.setOutputData(command.getTaskOutput().get());
            }

            updateTaskPort.updateTask(task);
            return true;
        } catch (TaskNotFoundError e) {
            // Check if it is in the repo instead (not loaded) and add to current task list
            System.out.println("Task not updated: " + e.toString());
            return false;
        } catch (Exception e) {
            System.out.println("Task not updated: " + e.toString());
            return false;
        }
    }
}
