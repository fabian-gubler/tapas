package ch.unisg.executorcompute.adapters;

import lombok.Getter;

public class ComputationJsonRepresentation {

    public static final String MEDIA_TYPE = "application/computation+json";

    @Getter
    private final String taskType;

    @Getter
    private final String taskLocation;

    @Getter
    private final String inputData;

    public ComputationJsonRepresentation(String taskType, String taskLocation, String inputData) {
        this.taskType = taskType;
        this.taskLocation = taskLocation;
        this.inputData = inputData;

    }

}
