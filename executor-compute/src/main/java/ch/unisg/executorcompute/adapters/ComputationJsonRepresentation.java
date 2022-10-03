package ch.unisg.executorcompute.adapters;

import lombok.Getter;

public class ComputationJsonRepresentation {

    public static final String MEDIA_TYPE = "application/computation+json";

    @Getter
    private final String type;

    @Getter
    private final double valueA;

    @Getter
    private final double valueB;

    public ComputationJsonRepresentation(String computationType, double valueA, double valueB) {
        this.type = computationType;
        this.valueA = valueA;
        this.valueB = valueB;
    }

}
