package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.adapter.in.messaging.NoMatchingExecutorException;
import ch.unisg.tapasroster.roster.application.port.in.MatchExecutorToReceivedTaskCommand;
import ch.unisg.tapasroster.roster.application.port.in.MatchExecutorToReceivedTaskUseCase;
import ch.unisg.tapasroster.roster.application.port.out.*;
import ch.unisg.tapasroster.roster.domain.Executor;
import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


/**
 * service to match the executor to the received Task.
 */
@RequiredArgsConstructor
@Component
@Transactional
@Service("MatchExecutorToReceivedTask")
public class MatchExecutorToReceivedTaskService implements MatchExecutorToReceivedTaskUseCase {

    private final NewTaskExecutionUseCase newTaskExecutionUseCase;
    private final UpdateTaskStatusUseCase updateTaskStatusUseCase;

    private final LaunchAuctionUseCase launchAuctionUseCase;

    private final RetrieveExecutorUseCase retrieveExecutorUseCase;

    private final AddRosterAssignmentPort addRosterAssignmentPort;

    @Autowired
    private Environment environment;


    /**
     * method to match the executor to the appropriate task, if an executor with a matching task is existing.
     * @param command - gets the data from the command class.
     */
    @Override
    @Async
    public void matchExecutorToReceivedTask(MatchExecutorToReceivedTaskCommand command) {

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

            // create roster entry
            RosterAssignment rosterAssignment = RosterAssignment.createRoster(
                new RosterAssignment.ExecutorEndpoint(matchedExecutorEndpoint.getValue()),
                new RosterAssignment.TaskLocation(command.getTaskLocation()),
                new RosterAssignment.AssignmentStatus("ASSIGNED"));
            System.out.println(rosterAssignment);
            addRosterAssignmentPort.addRosterAssignment(rosterAssignment);

           // updating task status to ASSIGNED
            UpdateTaskStatusCommand updateTaskStatusAssignedCommand =
                new UpdateTaskStatusCommand(UpdateTaskStatusCommand.Status.ASSIGNED, new RosterAssignment.TaskLocation(command.getTaskLocation()));
            updateTaskStatusUseCase.updateTaskStatusUseCase(updateTaskStatusAssignedCommand);

            System.out.println(environment.getProperty("baseuri") + "roster/updatetask/" + rosterAssignment.getAssignmentId().getValue());

            // executing task on given executor
            NewTaskExecutionCommand newTaskExecution = new NewTaskExecutionCommand(command.getTaskType(),
                matchedExecutorEndpoint.getValue(), command.getTaskLocation(), command.getInputData(), environment.getProperty("baseuri") + "roster/updatetask/" + rosterAssignment.getAssignmentId().getValue());
            String taskOutput = newTaskExecutionUseCase.newTaskExecutionUseCase(newTaskExecution);

            // updating task as RUNNING
            UpdateTaskStatusCommand updateTaskStatusRunningCommand =
                new UpdateTaskStatusCommand(UpdateTaskStatusCommand.Status.RUNNING, new RosterAssignment.TaskLocation(command.getTaskLocation()));
            updateTaskStatusUseCase.updateTaskStatusUseCase(updateTaskStatusRunningCommand);

        } else {
            RosterAssignment rosterAssignment = RosterAssignment.createRoster(
                new RosterAssignment.ExecutorEndpoint(""),
                new RosterAssignment.TaskLocation(command.getTaskLocation()),
                new RosterAssignment.AssignmentStatus("PENDING"));
            addRosterAssignmentPort.addRosterAssignment(rosterAssignment);
            System.out.println("No executor found for type " + command.getTaskType() + ", sending task to auction house ");
            int deadline = 60000;
            LaunchAuctionCommand launchAuctionCommand = new LaunchAuctionCommand(
                "",
                "uri",
                rosterAssignment.getTaskLocation().getValue(),
                command.getTaskType(),
                deadline,
                "OPEN");
            launchAuctionUseCase.launchAuction(launchAuctionCommand);
        }
    }
}
