package ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "executors")
public class MongoExecutorDocument {

    @Id
    public String executorId;

    public String endpoint;

    public Executor.Type executorType;

    public MongoExecutorDocument(String executorId, String endpoint, Executor.Type executorType) {

        this.executorId = executorId;
        this.endpoint = endpoint;
        this.executorType = executorType;

    }
}
