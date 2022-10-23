package ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb;

import ch.unisg.tapasroster.roster.domain.Roster;
import org.springframework.stereotype.Component;

@Component
class RosterMapper {

    Roster mapToDomainEntity(MongoRosterDocument roster) {
        return Roster.createRoster(
            new Roster.ExecutorEndpoint(roster.executorEndpoint),
            new Roster.TaskLocation(roster.taskLocation)
        );
    }

    MongoRosterDocument mapToMongoDocument(Roster roster) {
        return new MongoRosterDocument(
            roster.getRosterId().getValue(),
            roster.getTaskLocation().getValue(),
            roster.getExecutorEndpoint().getValue()
        );
    }
}
