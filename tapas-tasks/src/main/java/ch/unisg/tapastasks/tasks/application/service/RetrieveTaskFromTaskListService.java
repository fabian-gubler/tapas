package ch.unisg.tapastasks.tasks.application.service;

import ch.unisg.tapastasks.tasks.application.port.in.RetrieveTaskFromTaskListQuery;
import ch.unisg.tapastasks.tasks.application.port.in.RetrieveTaskFromTaskListUseCase;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.TaskList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional
@Service("RetrieveTaskFromTaskList")
public class RetrieveTaskFromTaskListService implements RetrieveTaskFromTaskListUseCase {


    @Override
    public Optional<Task> retrieveTaskFromTaskList(RetrieveTaskFromTaskListQuery query) {
        TaskList taskList = TaskList.getTapasTaskList();

        Optional<Task> task = taskList.retrieveTaskById(query.getTaskId());

        return task;
    }
}
