package ch.unisg.tapasexecutorpool.executorpool.application.service;

import ch.unisg.tapasexecutorpool.executorpool.adapter.in.messaging.NoMatchingExecutorException;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.MatchExecutorToReceivedTaskCommand;
import ch.unisg.tapasexecutorpool.executorpool.application.port.in.MatchExecutorToReceivedTaskUseCase;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.LoadExecutorPort;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewTaskExecutionEvent;
import ch.unisg.tapasexecutorpool.executorpool.application.port.out.NewTaskExecutionEventPort;
import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import ch.unisg.tapasexecutorpool.executorpool.domain.ExecutorNotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * service to match the executor to the received Task.
 */
@RequiredArgsConstructor
@Component
@Transactional
@Service("MatchExecutorToReceivedTask")
public class MatchExecutorToReceivedTaskService implements MatchExecutorToReceivedTaskUseCase {

    private final NewTaskExecutionEventPort newTaskExecutionEventPort;

    private final LoadExecutorPort loadExecutorFromRepositoryPort;

    /**
     * method to match the executor to the appropriate task, if an executor with a matching task is existing.
     * @param command - gets the data from the command class.
     * @return
     */
    @Override
    public Executor matchExecutorToReceivedTask(MatchExecutorToReceivedTaskCommand command) {

        Executor matchedExecutor;

        try {
            matchedExecutor = loadExecutorFromRepositoryPort.loadExecutorByType(command.getTaskType());

        } catch (ExecutorNotFoundError e) {
            throw new NoMatchingExecutorException("No executor found for type " + command.getTaskType());
        }

        NewTaskExecutionEvent newTaskExecution = new NewTaskExecutionEvent(command.getTaskType(),
            matchedExecutor.getTaskExecutionFullURI(), command.getTaskLocation(), command.getInputData());
        newTaskExecutionEventPort.publishNewTaskExecutionEvent(newTaskExecution);


        return matchedExecutor;
    }
}
