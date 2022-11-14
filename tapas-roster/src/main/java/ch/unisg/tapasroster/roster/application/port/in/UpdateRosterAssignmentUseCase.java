package ch.unisg.tapasroster.roster.application.port.in;

public interface UpdateRosterAssignmentUseCase {
    Boolean updateAssignment(UpdateRosterAssignmentCommand command);
}
