package ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb;

import ch.unisg.tapasroster.roster.application.port.out.AddRosterAssignmentPort;
import ch.unisg.tapasroster.roster.application.port.out.LoadRosterAssignmentPort;
import ch.unisg.tapasroster.roster.application.port.out.UpdateRosterAssignmentPort;
import ch.unisg.tapasroster.roster.domain.RosterNotFoundError;
import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RosterAssignmentPersistenceAdapter implements
    AddRosterAssignmentPort,
    LoadRosterAssignmentPort,
    UpdateRosterAssignmentPort {

    @Autowired
    private final RosterAssignmentRepository rosterAssignmentRepository;

    @Autowired
    private final RosterAssignmentMapper rosterAssignmentMapper;


    @Override
    public void addRosterAssignment(RosterAssignment rosterAssignment) {
        MongoRosterAssignmentDocument mongoRosterAssignmentDocument = rosterAssignmentMapper.mapToMongoDocument(rosterAssignment);
        rosterAssignmentRepository.save(mongoRosterAssignmentDocument);
    }

    @Override
    public RosterAssignment loadRosterAssignment(RosterAssignment.AssignmentId assignmentId) throws RosterNotFoundError {
        MongoRosterAssignmentDocument mongoRosterAssignmentDocument = rosterAssignmentRepository.findByRosterId(assignmentId.getValue());

        if(mongoRosterAssignmentDocument == null){
            throw new RosterNotFoundError();
        }
        return rosterAssignmentMapper.mapToDomainEntity(mongoRosterAssignmentDocument);
    }

    @Override
    public RosterAssignment updateAssignment(RosterAssignment.AssignmentId assignmentId, Optional<RosterAssignment.AssignmentStatus> newStatus, Optional<String> outputData) throws RosterNotFoundError {
       MongoRosterAssignmentDocument mongoRosterAssignmentDocument = rosterAssignmentRepository.findByRosterId(assignmentId.getValue());

        if(mongoRosterAssignmentDocument == null){
            throw new RosterNotFoundError();
        }

       if(newStatus.isPresent()){
           mongoRosterAssignmentDocument.assignmentStatus = newStatus.get().getValue();
       }

       if(outputData.isPresent()){
           mongoRosterAssignmentDocument.outputData = outputData.get();
       }

        rosterAssignmentRepository.save(mongoRosterAssignmentDocument);
       return rosterAssignmentMapper.mapToDomainEntity(mongoRosterAssignmentDocument);
    }
}
