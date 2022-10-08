package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.application.port.in.MatchExecutorToReceivedTaskCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.MatchExecutorToReceivedTaskUseCase;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewTaskExecutionEvent;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewTaskExecutionEventPort;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * service to match the executor to the received Task.
 */
@RequiredArgsConstructor
@Component
@Transactional
@Service("MatchExecutorToReceivedTask")
public class MatchExecutorToReceivedTaskService implements MatchExecutorToReceivedTaskUseCase {

    private final NewTaskExecutionEventPort newTaskExecutionEventPort;

    /**
     * method to match the executor to the appropriate task, if an executor with a matching task is existing.
     * @param command - gets the data from the command class.
     * @return
     */
    @Override
    public Optional<Executor> matchExecutorToReceivedTask(MatchExecutorToReceivedTaskCommand command) {

        ExecutorPool executorPool = ExecutorPool.getExecutorPool();
        Optional<Executor> matchedExecutor;

        matchedExecutor = executorPool.findAvailableExecutorFromTaskTypeString(command.getTaskType());

        // emit event to execute task if suitable executor is found
        if (matchedExecutor.isPresent()) {
            NewTaskExecutionEvent newTaskExecution = new NewTaskExecutionEvent(command.getTaskType(),
                matchedExecutor.get().getTaskExecutionFullURI(), command.getTaskLocation(), command.getInputData());
            newTaskExecutionEventPort.publishNewTaskExecutionEvent(newTaskExecution);
        }

        return matchedExecutor;
    }
}
