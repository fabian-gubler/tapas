package ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RosterRepository extends MongoRepository<MongoRosterDocument, String> {

    MongoRosterDocument findByRosterId(String rosterId);

}
