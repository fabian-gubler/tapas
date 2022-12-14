package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.application.port.in.UpdateRosterAssignmentCommand;
import ch.unisg.tapasroster.roster.application.port.in.UpdateRosterAssignmentUseCase;
import ch.unisg.tapasroster.roster.application.port.out.*;
import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import ch.unisg.tapasroster.roster.domain.RosterNotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Component
@Transactional
@Service("UpdateRosterAssignment")
public class UpdateRosterAssignmentService implements UpdateRosterAssignmentUseCase {

    private final UpdateRosterAssignmentPort updateRosterAssignmentPort;

    private final UpdateTaskStatusUseCase updateTaskStatusUseCase;

    private final UpdateTaskOutputUseCase updateTaskOutputUseCase;

    @Override
    public Boolean updateAssignment(UpdateRosterAssignmentCommand command) {
        try {

            RosterAssignment updatedRosterAssignment = updateRosterAssignmentPort.updateAssignment(command.getAssignmentId(), command.getNewStatus(), command.getOutputData());

            // update task outputdata in tasklist
            if (command.getOutputData().isPresent()) {
                // executor provided outputdata, outputdata is updated on task in tasklist
                UpdateTaskOutputCommand updateTaskOutputCommand = new UpdateTaskOutputCommand(
                    command.getOutputData().get(),
                    new RosterAssignment.TaskLocation(updatedRosterAssignment.getTaskLocation().getValue()));
                updateTaskOutputUseCase.updateTaskOutputUseCase(updateTaskOutputCommand);
            }

            // update original task uri
            if(updatedRosterAssignment.getOriginalTaskUri() != null && !updatedRosterAssignment.getOriginalTaskUri().getValue().equals("")) {
                if (command.getOutputData().isPresent()) {
                    UpdateTaskOutputCommand updateTaskOutputAtOriginCommand = new UpdateTaskOutputCommand(
                        command.getOutputData().get(),
                        new RosterAssignment.TaskLocation(updatedRosterAssignment.getOriginalTaskUri().getValue()));
                    updateTaskOutputUseCase.updateTaskOutputUseCase(updateTaskOutputAtOriginCommand);
                }
            }

            return true;
        } catch (RosterNotFoundError e){
            System.out.println("RosterAssignment not updated: " + e);
            return false;
        } catch (Exception e) {
            System.out.println("RosterAssignment not updated: " + e);
            return false;
        }
    }
}
