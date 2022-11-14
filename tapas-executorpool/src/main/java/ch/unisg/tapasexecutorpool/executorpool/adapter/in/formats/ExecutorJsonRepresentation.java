package ch.unisg.tapasexecutorpool.executorpool.adapter.in.formats;

import ch.unisg.tapasexecutorpool.executorpool.domain.Executor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to expose and consume representations of tasks through the HTTP interface. The
 * representations conform to the custom JSON-based media type "application/task+json". The media type
 * is just an identifier and can be registered with
 * <a href="https://www.iana.org/assignments/media-types/">IANA</a> to promote interoperability.
 */
final public class ExecutorJsonRepresentation {
    // The media type used for this task representation format
    public static final String MEDIA_TYPE = "application/executor+json";

    // A task identifier specific to our implementation (e.g., a UUID). This identifier is then used
    // to generate the task's URI. URIs are standard uniform identifiers and use a universal syntax
    // that can be referenced (and dereferenced) independent of context. In our uniform HTTP API,
    // we identify tasks via URIs and not implementation-specific identifiers.
    @Getter
    @Setter
    private String executorId;

    // A string that represents the task's name
    @Getter
    private final String executorType;

    // A string that identifies the task's type. This string could also be a URI, but it's not constrained to be a URI.
    // The task's type can be used to assign executors to tasks, to decide what tasks to bid for, etc.
    @Getter
    private final String endpoint;


    /**
     * Instantiate a task representation with a task name and type.
     *
     * @param executorType string that represents the executor type, i.e. the type of tasks the executor can execute, allowed values: api, COMPUTE
     * @param endpoint     string that represents the location where the executor service can be reached
     */
    public ExecutorJsonRepresentation(String executorType, String endpoint) {
        this.executorType = executorType;
        this.endpoint = endpoint;
    }

    /**
     * Instantiate a task representation from a domain concept.
     *
     * @param executor the task
     */
    public ExecutorJsonRepresentation(Executor executor) {
        this(executor.getExecutorType().getValue(), executor.getEndpoint().getValue());

        this.executorId = executor.getExecutorId().getValue();
    }

    /**
     * Convenience method used to serialize a task provided as a domain concept in the format exposed
     * through the uniform HTTP API.
     *
     * @param executor the executor as defined in the domain
     * @return a string serialization using the JSON-based representation format defined for tasks
     * @throws JsonProcessingException if a runtime exception occurs during object serialization
     */
    public static String serialize(Executor executor) throws JsonProcessingException {
        ExecutorJsonRepresentation representation = new ExecutorJsonRepresentation(executor);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(representation);
    }
}
