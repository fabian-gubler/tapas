package ch.unisg.tapasroster.roster.application.port.out;


public class NewTaskExecutionCommand {

    public String taskType;

    public String taskExecutionURI;
    public String taskLocation;

    public String inputData;

    public String returnLocation;


    public NewTaskExecutionCommand(String taskType, String taskExecutionURI, String taskLocation, String inputData, String returnLocation) {
        this.taskType = taskType;
        this.taskLocation = taskLocation;
        this.taskExecutionURI = taskExecutionURI;
        this.inputData = inputData;
        this.returnLocation = returnLocation;
    }
}
