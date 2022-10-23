package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.adapter.in.messaging.NoMatchingExecutorException;
import ch.unisg.tapasroster.roster.application.port.in.MatchExecutorToReceivedTaskCommand;
import ch.unisg.tapasroster.roster.application.port.in.MatchExecutorToReceivedTaskUseCase;
import ch.unisg.tapasroster.roster.application.port.out.*;
import ch.unisg.tapasroster.roster.domain.Executor;
import ch.unisg.tapasroster.roster.domain.Roster;
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

    private final NewTaskExecutionUseCase newTaskExecutionUseCase;

    private final RetrieveExecutorUseCase retrieveExecutorUseCase;

    private final AddRosterPort addRosterPort;


    /**
     * method to match the executor to the appropriate task, if an executor with a matching task is existing.
     * @param command - gets the data from the command class.
     * @return
     */
    @Override
    public Roster.ExecutorEndpoint matchExecutorToReceivedTask(MatchExecutorToReceivedTaskCommand command) {

        Executor.Endpoint matchedExecutorEndpoint;

        System.out.println("Retrieving executor from executorpool with type: " + command.getTaskType());

        try {
            RetrieveExecutorQuery retrieveExecutorQuery = new RetrieveExecutorQuery(command.getTaskType());
            matchedExecutorEndpoint = retrieveExecutorUseCase.retrieveExecutorUseCase(retrieveExecutorQuery);
        } catch (Exception e) {
            System.out.println("No executor found for type " + command.getTaskType());
            throw new NoMatchingExecutorException("No executor found for type " + command.getTaskType());
        }

        if (matchedExecutorEndpoint != null) {
            System.out.println("Executor found, executing task");

            NewTaskExecutionCommand newTaskExecution = new NewTaskExecutionCommand(command.getTaskType(),
                matchedExecutorEndpoint.getValue(), command.getTaskLocation(), command.getInputData());
            newTaskExecutionUseCase.newTaskExecutionUseCase(newTaskExecution);

            // create roster entry
            Roster roster = Roster.createRoster(new Roster.ExecutorEndpoint(matchedExecutorEndpoint.getValue()), new Roster.TaskLocation(command.getTaskLocation()));
            addRosterPort.addRoster(roster);
            return roster.getExecutorEndpoint();
        } else {
            System.out.println("No executor found for type " + command.getTaskType() + ", sending task to auction house ");
            // todo: set roster state to pending/looking for executor, send task to AuctionHouse
            return null;
        }
    }
}
