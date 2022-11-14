package ch.unisg.tapasroster.roster.adapter.in.formats;

import lombok.Getter;

public class UpdateRosterAssignmentJsonRepresentation {


    // A string that identifies the task's type. This string could also be a URI (e.g., defined in some
    // Web ontology, as we shall see later in the course), but it's not constrained to be a URI.
    // The task's type can be used to assign executors to tasks, to decide what tasks to bid for, etc.
    @Getter
    private final boolean success;

    @Getter
    private final String outputData;


    /**
     * Instantiate a task representation with a task name and type.
     *
     */
    public UpdateRosterAssignmentJsonRepresentation(boolean success, String outputData) {
        this.success = success;
        this.outputData = outputData;
    }
}
