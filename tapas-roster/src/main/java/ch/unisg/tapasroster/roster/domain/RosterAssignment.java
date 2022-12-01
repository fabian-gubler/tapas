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
    private final AssignmentId assignmentId;

    @Getter @Setter
    private String outputData;

    @Getter @Setter
    private AssignmentStatus assignmentStatus;

    @Getter
    private OriginalTaskUri originalTaskUri;

    public RosterAssignment(ExecutorEndpoint executorEndpoint, TaskLocation taskLocation, AssignmentId assignmentId) {
        this.executorEndpoint = executorEndpoint;
        this.taskLocation = taskLocation;
        this.assignmentId = assignmentId;
    }

    public RosterAssignment(ExecutorEndpoint executorEndpoint, TaskLocation taskLocation, AssignmentId assignmentId, String outputData, AssignmentStatus status, OriginalTaskUri originalTaskUri) {
        this.executorEndpoint = executorEndpoint;
        this.taskLocation = taskLocation;
        this.assignmentId = assignmentId;
        this.outputData = outputData;
        this.assignmentStatus = status;
        this.originalTaskUri = originalTaskUri;
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
    public static class OriginalTaskUri {
        String value;
    }

    @Value
    public static class AssignmentStatus {
        String value;
    }

    @Value
    public static class AssignmentId {
        String value;
    }


    public static RosterAssignment createRosterAssignment(ExecutorEndpoint executorEndpoint, TaskLocation taskLocation, OriginalTaskUri originalTaskUri) {
        return new RosterAssignment(executorEndpoint, taskLocation, new AssignmentId(UUID.randomUUID().toString()), "", new AssignmentStatus("UNASSIGNED"), originalTaskUri);
    }
    public static RosterAssignment createRosterAssignment(ExecutorEndpoint executorEndpoint, TaskLocation taskLocation, AssignmentStatus status, OriginalTaskUri originalTaskUri) {
        return new RosterAssignment(executorEndpoint, taskLocation, new AssignmentId(UUID.randomUUID().toString()), "", status, originalTaskUri);
    }
}
