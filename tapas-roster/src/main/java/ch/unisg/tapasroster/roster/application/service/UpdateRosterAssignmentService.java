package ch.unisg.tapasroster.roster.application.service;

import ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb.RosterAssignmentRepository;
import ch.unisg.tapasroster.roster.application.port.in.UpdateRosterAssignmentCommand;
import ch.unisg.tapasroster.roster.application.port.in.UpdateRosterAssignmentUseCase;
import ch.unisg.tapasroster.roster.application.port.out.LoadRosterAssignmentPort;
import ch.unisg.tapasroster.roster.application.port.out.UpdateRosterAssignmentPort;
import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import ch.unisg.tapasroster.roster.domain.RosterNotFoundError;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
@Service("UpdateRosterAssignment")
public class UpdateRosterAssignmentService implements UpdateRosterAssignmentUseCase {

    private final UpdateRosterAssignmentPort updateRosterAssignmentPort;

    private final LoadRosterAssignmentPort loadRosterAssignmentPort;

    @Override
    @Async
    public Boolean updateTask(UpdateRosterAssignmentCommand command) {
        try {
            RosterAssignment rosterAssignment = loadRosterAssignmentPort.loadRoster(new RosterAssignment.RosterId(command.getAssignmentId()));

            if(command.getNewStatus().isPresent()) {
                rosterAssignment.setAssignmentStatus(command.getNewStatus().get());
            }

            if(command.getOutputData().isPresent()){
                rosterAssignment.setOutputData(command.getOutputData().get());
            }

            updateRosterAssignmentPort.updateAssignment(rosterAssignment);
            return true;
        } catch (RosterNotFoundError e){
            System.out.println("RosterAssignment not updated: " + e);
            return false;
        } catch (Exception e) {
            System.out.println("RosterAssignment not updated: " + e.toString());
            return false;
        }
    }
}
