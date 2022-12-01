package ch.unisg.tapas.auctionhouse.adapter.common.formats;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to expose and consume representations of tasks through the HTTP interface. The
 * representations conform to the custom JSON-based media type "application/task+json". The media type
 * is just an identifier and can be registered with
 * <a href="https://www.iana.org/assignments/media-types/">IANA</a> to promote interoperability.
 */
final public class TaskJsonRepresentation {
    // The media type used for this task representation format
    public static final String MEDIA_TYPE = "application/task+json";

    // A task identifier specific to our implementation (e.g., a UUID). This identifier is then used
    // to generate the task's URI. URIs are standard uniform identifiers and use a universal syntax
    // that can be referenced (and dereferenced) independent of context. In our uniform HTTP API,
    // we identify tasks via URIs and not implementation-specific identifiers.
    @Getter @Setter
    private String taskId;

    // A string that represents the task's name
    @Getter
    private final String taskName;

    // A string that identifies the task's type. This string could also be a URI (e.g., defined in some
    // Web ontology, as we shall see later in the course), but it's not constrained to be a URI.
    // The task's type can be used to assign executors to tasks, to decide what tasks to bid for, etc.
    @Getter
    private final String taskType;

    // The task's status: OPEN, ASSIGNED, RUNNING, or EXECUTED (see Task.Status)
    @Getter @Setter
    private String taskStatus;

    // If this task is a delegated task (i.e., a shadow of another task), this URI points to the
    // original task. Because URIs are standard and uniform, we can just dereference this URI to
    // retrieve a representation of the original task.
    @Getter @Setter
    private String originalTaskUri;

    // The service provider who executes this task. The service provider is a any string that identifies
    // a TAPAS group (e.g., tapas-group1). This identifier could also be a URI (if we have a good reason
    // for it), but it's not constrained to be a URI.
    @Getter @Setter
    private String serviceProvider;

    // A string that provides domain-specific input data for this task. In the context of this project,
    // we can parse and interpret the input data based on the task's type.
    @Getter @Setter
    private String inputData;

    // A string that provides domain-specific output data for this task. In the context of this project,
    // we can parse and interpret the output data based on the task's type.
    @Getter @Setter
    private String outputData;

    @Getter @Setter
    private String taskListUri;


    public TaskJsonRepresentation(String taskName, String taskType, String taskStatus, String inputData, String outputData, String taskListUri, String originalTaskUri) {
        this.taskName = taskName;
        this.taskType = taskType;

        this.taskStatus = taskStatus;
        this.inputData = inputData;
        this.outputData = outputData;
        this.taskListUri = taskListUri;
        this.originalTaskUri = originalTaskUri;
    }


    public static String serialize(TaskJsonRepresentation task) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(task);
    }

    public static TaskJsonRepresentation deserialize(String taskString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(taskString);

        TaskJsonRepresentation representation = mapper.treeToValue(data, TaskJsonRepresentation.class);

        return representation;
    }
}
