package ch.unisg.tapasexecutorpool.executorpool.application.port.out;

public class NewExecutorAddedEvent {

    public String executorId;

    public String executorType;

    public NewExecutorAddedEvent(String executorId, String executorType) {
        this.executorId = executorId;
        this.executorType = executorType;
    }
}
