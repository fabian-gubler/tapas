package ch.unisg.tapasroster.roster.adapter.in.formats;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class NewTaskJsonRepresentation {

    // A task identifier specific to our implementation (e.g., a UUID). This identifier is then used
    // to generate the task's URI. URIs are standard uniform identifiers and use a universal syntax
    // that can be referenced (and dereferenced) independent of context. In our uniform HTTP API,
    // we identify tasks via URIs and not implementation-specific identifiers.
    @Getter
    @Setter
    private final String taskLocation;

    // A string that identifies the task's type. This string could also be a URI (e.g., defined in some
    // Web ontology, as we shall see later in the course), but it's not constrained to be a URI.
    // The task's type can be used to assign executors to tasks, to decide what tasks to bid for, etc.
    @Getter
    private final String taskType;

    // the name of the tasklist
    @Getter
    private final String tasklist;

    // inputData of the task
    @Getter
    private final String inputData;


    /**
     * Instantiate a task representation with a task name and type.
     *
     */
    public NewTaskJsonRepresentation(String taskLocation, String taskType, String tasklist, String inputData) {
        this.taskLocation = taskLocation;
        this.taskType = taskType;
        this.tasklist = tasklist;
        this.inputData = inputData;
    }
}
