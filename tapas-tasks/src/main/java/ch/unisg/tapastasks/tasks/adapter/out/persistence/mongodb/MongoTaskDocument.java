package ch.unisg.tapastasks.tasks.adapter.out.persistence.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tasks")
public class MongoTaskDocument {

    @Id
    public String taskId;

    public String taskName;
    public String taskType;
    public String taskStatus;

    public String inputData;

    public String outputData;


    public String taskListName;



    public MongoTaskDocument(String taskId, String taskName, String taskType,
                             String inputData, String outputData,
                             String taskStatus, String taskListName) {

        this.taskId = taskId;
        this.taskName = taskName;
        this.taskType = taskType;
        this.inputData = inputData;
        this.outputData = outputData;
        this.taskStatus = taskStatus;
        this.taskListName = taskListName;

    }
}
