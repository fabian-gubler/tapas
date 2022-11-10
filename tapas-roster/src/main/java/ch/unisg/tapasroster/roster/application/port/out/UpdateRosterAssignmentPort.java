package ch.unisg.tapasroster.roster.application.port.out;

import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import ch.unisg.tapasroster.roster.domain.RosterNotFoundError;

import java.util.Optional;

public interface UpdateRosterAssignmentPort {

    RosterAssignment updateAssignment(RosterAssignment.AssignmentId assignmentId, Optional<RosterAssignment.AssignmentStatus> newStatus, Optional<String> outputData) throws RosterNotFoundError;
}
