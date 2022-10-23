package ch.unisg.tapasroster.roster.domain;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

/**This is a domain entity
 * **/
public class Roster {

    @Getter
    private final ExecutorEndpoint executorEndpoint;

    @Getter
    private final TaskLocation taskLocation;

    @Getter
    private final RosterId rosterId;

    public Roster(ExecutorEndpoint executorEndpoint, TaskLocation taskLocation, RosterId rosterId) {
        this.executorEndpoint = executorEndpoint;
        this.taskLocation = taskLocation;
        this.rosterId = rosterId;
    }

    @Value
    public static class ExecutorEndpoint {
        String value;
    }

    @Value
    public static class TaskLocation {
        String value;
    }

    @Value
    static class Status {
        String value;
    }

    @Value public static class RosterId {
        String value;
    }


    public static Roster createRoster(ExecutorEndpoint executorEndpoint, TaskLocation taskLocation) {
        return new Roster(executorEndpoint, taskLocation, new RosterId(UUID.randomUUID().toString()));
    }
}
