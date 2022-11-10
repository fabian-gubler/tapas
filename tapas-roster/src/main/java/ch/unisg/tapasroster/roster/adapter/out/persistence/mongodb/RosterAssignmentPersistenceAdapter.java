package ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb;

// import ch.unisg.tapasroster.roster.application.port.out.AddExecutorPort;
import ch.unisg.tapasroster.roster.application.port.out.AddRosterAssignmentPort;
import ch.unisg.tapasroster.roster.application.port.out.LoadRosterAssignmentPort;
import ch.unisg.tapasroster.roster.application.port.out.UpdateRosterAssignmentPort;
import ch.unisg.tapasroster.roster.domain.RosterNotFoundError;
import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RosterAssignmentPersistenceAdapter implements
    AddRosterAssignmentPort,
    LoadRosterAssignmentPort,
    UpdateRosterAssignmentPort {

    @Autowired
    private final RosterAssignmentRepository rosterAssignmentRepository;

    @Autowired
    private final RosterAssignmentMapper rosterAssignmentMapperMapper;


    @Override
    public void addRoster(RosterAssignment rosterAssignment) {
        MongoRosterAssignmentDocument mongoRosterAssignmentDocument = rosterAssignmentMapperMapper.mapToMongoDocument(rosterAssignment);
        rosterAssignmentRepository.save(mongoRosterAssignmentDocument);
    }

    @Override
    public RosterAssignment loadRoster(RosterAssignment.RosterId rosterId) throws RosterNotFoundError {
        MongoRosterAssignmentDocument mongoRosterAssignmentDocument = rosterAssignmentRepository.findByRosterId(rosterId.getValue());

        if(mongoRosterAssignmentDocument == null){
            throw new RosterNotFoundError();
        }
        return rosterAssignmentMapperMapper.mapToDomainEntity(mongoRosterAssignmentDocument);
    }

    @Override
    public void updateAssignment(RosterAssignment rosterAssignment) throws RosterNotFoundError {
       MongoRosterAssignmentDocument mongoRosterAssignmentDocument = rosterAssignmentRepository.findByRosterId(rosterAssignment.getRosterId().getValue());

       if(rosterAssignment.getAssignmentStatus() != null){
           mongoRosterAssignmentDocument.assignmentStatus = rosterAssignment.getAssignmentStatus().getValue();
       }

       if(rosterAssignment.getOutputData() != null){
           mongoRosterAssignmentDocument.outputData = rosterAssignment.getOutputData();
       }

        rosterAssignmentRepository.save(mongoRosterAssignmentDocument);
    }
}
