package ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RosterAssignmentRepository extends MongoRepository<MongoRosterAssignmentDocument, String> {

    MongoRosterAssignmentDocument findByRosterId(String rosterId);

}
