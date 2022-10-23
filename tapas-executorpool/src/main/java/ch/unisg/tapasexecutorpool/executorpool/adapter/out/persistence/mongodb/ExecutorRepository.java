package ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutorRepository extends MongoRepository<MongoExecutorDocument, String> {

    MongoExecutorDocument findByExecutorId(String executorId);

    MongoExecutorDocument findByExecutorType(String executorType);
    MongoExecutorDocument findFirstByExecutorType(String executorType);
}
