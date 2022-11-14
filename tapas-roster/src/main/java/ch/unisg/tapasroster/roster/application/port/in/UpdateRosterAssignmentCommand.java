package ch.unisg.tapasroster.roster.application.port.in;

import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Value
public class UpdateRosterAssignmentCommand {
    @NotNull
    private final RosterAssignment.AssignmentId assignmentId;

    @NotNull
    private final Optional<RosterAssignment.AssignmentStatus> newStatus;

    private final Optional<String> outputData;

    /**
     * Constructor where the values of the data field are initialised and validated.
     * @param assignmentId
     * @param newStatus
     * @param outputData
     */
    public UpdateRosterAssignmentCommand(RosterAssignment.AssignmentId assignmentId, Optional<RosterAssignment.AssignmentStatus> newStatus, Optional<String> outputData) {
        this.assignmentId = assignmentId;
        this.newStatus = newStatus;
        this.outputData = outputData;

    }
}
