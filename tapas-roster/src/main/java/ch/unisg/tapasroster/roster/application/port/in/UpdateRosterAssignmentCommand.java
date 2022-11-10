package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Value
public class UpdateRosterAssignmentCommand {
    @NotNull
    private final String assignmentId;

    @NotNull
    private final Optional<RosterAssignment.AssignmentStatus> newStatus;

    private final Optional<String> outputData;

    /**
     * Constructor where the values of the data field are initialised and validated.
     * @param taskId
     * @param newStatus
     * @param outputData
     */
    public UpdateRosterAssignmentCommand(String taskId, Optional<RosterAssignment.AssignmentStatus> newStatus, Optional<String> outputData) {
        this.assignmentId = taskId;
        this.newStatus = newStatus;
        this.outputData = outputData;

    }
}
