package ch.unisg.executorjoke.formats;

import lombok.Getter;

public class TaskInputRepresentation {

    public static final String MEDIA_TYPE = "application/json";

    @Getter
    private final String inputData;

    public TaskInputRepresentation(String taskId, String inputData) {
        this.inputData = inputData;
    }
}
