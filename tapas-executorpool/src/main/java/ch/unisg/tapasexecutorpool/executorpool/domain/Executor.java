package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity**/
public class Executor {
    public enum Type {
        JOKE, COMPUTE
    }

    @Getter
    private final ExecutorId executorId;

    @Getter
    private final ExecutorType executorType;


    @Getter
    private final Endpoint endpoint;

    public Executor(ExecutorType executorType, Endpoint endpoint) {
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
        this.executorType = executorType;
        this.endpoint = endpoint;
    }


    public static Executor createExecutorWithTypeAndEnpoint(ExecutorType executorType, Endpoint endpoint) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Executor: " + executorType.getValue() + " " + endpoint.getValue());
        return new Executor(executorType, endpoint);
    }


    @Value
    public static class ExecutorId {
        String value;
    }

    @Value
    public static class Endpoint {
        String value;
    }

    @Value
    public static class ExecutorType {
        Type value;
    }

}
