package ch.unisg.tapasexecutorpool.executorpool.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity
 * **/
public class Executor {
    // The enum Type defines the different states an Executor can have.
    public enum Type {
        JOKE, COMPUTE, COMPUTE_ADD, COMPUTE_MULTIPLY, COMPUTE_SUBTRACT, COMPUTE_DIVIDE
    }

    @Getter
    private final ExecutorId executorId;

    @Getter
    private final ExecutorType executorType;

    @Getter
    private final Endpoint endpoint;

    // constructor of the Executor class
    public Executor(ExecutorType executorType, Endpoint endpoint) {
        this.executorId = new ExecutorId(UUID.randomUUID().toString());
        this.executorType = executorType;
        this.endpoint = endpoint;
    }

    /**
     * This method creates an executor with a type and an
     * endpoint with a debuf message to see that the request
     * has reached the right method in the core.
     * @param executorType
     * @param endpoint
     * @return Executor - returns an object of the Executor class
     */
    public static Executor createExecutorWithTypeAndEnpoint(ExecutorType executorType, Endpoint endpoint) {
        //This is a simple debug message to see that the request has reached the right method in the core
        System.out.println("New Executor: " + executorType.getValue() + " " + endpoint.getValue());
        return new Executor(executorType, endpoint);
    }

    /**
     * @return - Returns the String URI of an Executor object.
     */
    public String getTaskExecutionFullURI() {
        return this.getEndpoint().getValue();
    }

     // subclass for the Executor class, which provides automatically a Getter and Setter method.
    @Value
    public static class ExecutorId {
        String value;
    }

    // subclass for the Executor class, which provides automatically a Getter and Setter method.
    @Value
    public static class Endpoint {
        String value;
    }

    // subclass for the Executor class, which provides automatically a Getter and Setter method.
    @Value
    public static class ExecutorType {
        Type value;
    }

}
