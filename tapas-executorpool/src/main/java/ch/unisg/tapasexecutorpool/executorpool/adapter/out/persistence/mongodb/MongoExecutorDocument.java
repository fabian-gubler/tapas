package ch.unisg.tapasexecutorpool.executorpool.adapter.out.persistence.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "executors")
public class MongoExecutorDocument {

    @Id
    public String executorId;

    public String endpoint;

    public String executorType;

    public MongoExecutorDocument(String executorId, String endpoint, String executorType) {

        this.executorId = executorId;
        this.endpoint = endpoint;
        this.executorType = executorType;

    }
}
