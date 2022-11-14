package ch.unisg.tapasroster.roster.application.port.in;

import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * Command where the task is initialised and validated.
 */
@Value
public class MatchExecutorToReceivedTaskCommand {
    @NotNull
    private final String taskType;

    @NotNull
    private final String taskLocation;

    private final String inputData;

    /**
     * Constructor where the values of the data field are initialised and validated.
     * @param taskType
     * @param taskLocation
     * @param inputData
     */
    public MatchExecutorToReceivedTaskCommand(String taskType, String taskLocation, String inputData) {
        this.taskType = taskType;
        this.taskLocation = taskLocation;
        this.inputData = inputData;
    }
}
