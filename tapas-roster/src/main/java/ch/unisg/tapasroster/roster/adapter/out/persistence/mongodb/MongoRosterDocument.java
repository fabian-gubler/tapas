package ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "executors")
public class MongoRosterDocument {

    @Id
    public String rosterId;

    public String executorEndpoint;

    public String taskLocation;

    public MongoRosterDocument(String rosterId, String executorEndpoint, String taskLocation) {
        this.rosterId = rosterId;
        this.executorEndpoint = executorEndpoint;
        this.taskLocation = taskLocation;
    }
}
