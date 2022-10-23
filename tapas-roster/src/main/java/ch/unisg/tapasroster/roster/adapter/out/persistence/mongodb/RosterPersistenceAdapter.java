package ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb;

// import ch.unisg.tapasroster.roster.application.port.out.AddExecutorPort;
import ch.unisg.tapasroster.roster.application.port.out.AddRosterPort;
import ch.unisg.tapasroster.roster.application.port.out.LoadRosterPort;
import ch.unisg.tapasroster.roster.domain.RosterNotFoundError;
import ch.unisg.tapasroster.roster.domain.Roster;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RosterPersistenceAdapter implements
    AddRosterPort,
    LoadRosterPort {

    @Autowired
    private final RosterRepository executorRepository;

    @Autowired
    private final RosterMapper rosterMapper;


    @Override
    public void addRoster(Roster roster) {
        MongoRosterDocument mongoRosterDocument = rosterMapper.mapToMongoDocument(roster);
        executorRepository.save(mongoRosterDocument);
    }

    @Override
    public Roster loadRoster(Roster.RosterId rosterId) throws RosterNotFoundError {
        MongoRosterDocument mongoRosterDocument = executorRepository.findByRosterId(rosterId.getValue());

        if(mongoRosterDocument == null){
            throw new RosterNotFoundError();
        }
        return rosterMapper.mapToDomainEntity(mongoRosterDocument);
    }

}
