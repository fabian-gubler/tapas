package ch.unisg.tapasexecutorpool.executorpool.application.port.out;


import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;

public class NewTaskExecutionEvent {

    public String taskType;

    public String taskExecutionURI;
    public String taskLocation;

    public String inputData;

    public NewTaskExecutionEvent(String taskType, String taskExecutionURI, String taskLocation, String inputData) {
        this.taskType = taskType;
        this.taskLocation = taskLocation;
        this.taskExecutionURI = taskExecutionURI;
        this.inputData = inputData;
    }
}
