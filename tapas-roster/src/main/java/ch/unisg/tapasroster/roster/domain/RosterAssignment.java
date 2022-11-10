package ch.unisg.tapasroster.roster.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity
 * **/
public class RosterAssignment {

    @Getter
    private final ExecutorEndpoint executorEndpoint;

    @Getter
    private final TaskLocation taskLocation;

    @Getter
    private final RosterId rosterId;

    @Getter @Setter
    private String outputData;

    @Getter @Setter
    private AssignmentStatus assignmentStatus;

    public RosterAssignment(ExecutorEndpoint executorEndpoint, TaskLocation taskLocation, RosterId rosterId) {
        this.executorEndpoint = executorEndpoint;
        this.taskLocation = taskLocation;
        this.rosterId = rosterId;
    }

    public RosterAssignment(ExecutorEndpoint executorEndpoint, TaskLocation taskLocation, RosterId rosterId, String outputData, AssignmentStatus status) {
        this.executorEndpoint = executorEndpoint;
        this.taskLocation = taskLocation;
        this.rosterId = rosterId;
        this.outputData = outputData;
        this.assignmentStatus = status;
    }


    @Value
    public static class ExecutorEndpoint {
        String value;
    }

    @Value
    public static class TaskLocation {
        String value;
    }

    @Value
    public static class AssignmentStatus {
        String value;
    }

    @Value
    public static class RosterId {
        String value;
    }


    public static RosterAssignment createRoster(ExecutorEndpoint executorEndpoint, TaskLocation taskLocation) {
        return new RosterAssignment(executorEndpoint, taskLocation, new RosterId(UUID.randomUUID().toString()));
    }
}
