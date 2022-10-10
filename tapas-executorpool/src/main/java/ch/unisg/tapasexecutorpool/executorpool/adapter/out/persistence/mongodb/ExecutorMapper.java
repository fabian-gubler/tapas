package ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import org.springframework.stereotype.Component;

@Component
class ExecutorMapper {

    Executor mapToDomainEntity(MongoExecutorDocument executor) {
        return Executor.createExecutorWithTypeAndEnpoint(
            new Executor.ExecutorType(executor.executorType),
            new Executor.Endpoint(executor.endpoint)
        );
    }

    MongoExecutorDocument mapToMongoDocument(Executor executor) {
        return new MongoExecutorDocument(
            executor.getExecutorId().getValue(),
            executor.getEndpoint().getValue(),
            executor.getExecutorType().getValue()
        );
    }
}
