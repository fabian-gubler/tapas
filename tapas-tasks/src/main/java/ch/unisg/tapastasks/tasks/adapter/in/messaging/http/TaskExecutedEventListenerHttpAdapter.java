package ch.unisg.tapastasks.tasks.adapter.in.messaging.http;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonPatchRepresentation;
import ch.unisg.tapastasks.tasks.application.handler.TaskExecutedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskExecutedEventHandler;
import ch.unisg.tapastasks.tasks.domain.Task;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

/**
 * Listener for task executed events. A task executed event corresponds to a JSON Patch that attempts
 * to change the task's status to EXECUTED, and may also add an
 * output result.
 *
 * See also {@link TaskExecutedEvent}, {@link Task}, and {@link TaskEventHttpDispatcher}.
 */
public class TaskExecutedEventListenerHttpAdapter extends TaskEventListener {

    public boolean handleTaskEvent(String taskId, JsonNode payload) {
        TaskJsonPatchRepresentation representation = new TaskJsonPatchRepresentation(payload);

        Optional<Task.OutputData> outputData = representation.extractFirstOutputDataAddition();

        TaskExecutedEvent taskExecutedEvent = new TaskExecutedEvent(new Task.TaskId(taskId), outputData);
        TaskExecutedEventHandler taskExecutedEventHandler = new TaskExecutedHandler();

        return taskExecutedEventHandler.handleTaskExecuted(taskExecutedEvent);
    }
}
