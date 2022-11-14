package ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb;

import ch.unisg.tapasroster.roster.domain.RosterAssignment;
import org.springframework.stereotype.Component;

@Component
class RosterAssignmentMapper {

    RosterAssignment mapToDomainEntity(MongoRosterAssignmentDocument roster) {
        return RosterAssignment.createRoster(
            new RosterAssignment.ExecutorEndpoint(roster.executorEndpoint),
            new RosterAssignment.TaskLocation(roster.taskLocation)
        );
    }

    MongoRosterAssignmentDocument mapToMongoDocument(RosterAssignment rosterAssignment) {

        return new MongoRosterAssignmentDocument(
            rosterAssignment.getAssignmentId().getValue(),
            rosterAssignment.getExecutorEndpoint().getValue(),
            rosterAssignment.getTaskLocation().getValue(),
            rosterAssignment.getAssignmentStatus() != null ? rosterAssignment.getAssignmentStatus().getValue() : "",
            rosterAssignment.getOutputData()
        );
    }
}
