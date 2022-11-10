package ch.unisg.tapasroster.roster.adapter.out.persistence.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rosters")
public class MongoRosterAssignmentDocument {

    @Id
    public String rosterId;
    public String executorEndpoint;

    public String taskLocation;

    public String assignmentStatus;

    public String outputData;

    public MongoRosterAssignmentDocument(String rosterId, String executorEndpoint, String taskLocation, String assignmentStatus, String outputData) {
        this.rosterId = rosterId;
        this.executorEndpoint = executorEndpoint;
        this.taskLocation = taskLocation;
        this.assignmentStatus = assignmentStatus;
        this.outputData = outputData;
    }
}
