package ch.unisg.tapastasks.tasks.adapter.in.messaging.http;

import ch.unisg.tapastasks.tasks.adapter.in.formats.TaskJsonPatchRepresentation;
import ch.unisg.tapastasks.tasks.application.handler.TaskStartedHandler;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEvent;
import ch.unisg.tapastasks.tasks.application.port.in.TaskStartedEventHandler;
import ch.unisg.tapastasks.tasks.domain.Task;
import ch.unisg.tapastasks.tasks.domain.Task.TaskId;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Listener for task started events. A task started event corresponds to a JSON Patch that attempts
 * to change the task's status to RUNNING.
 *
 * See also {@link TaskStartedEvent}, {@link Task}, and {@link TaskEventHttpDispatcher}.
 */
public class TaskStartedEventListenerHttpAdapter extends TaskEventListener {

    public boolean handleTaskEvent(String taskId, JsonNode payload) {
        TaskJsonPatchRepresentation representation = new TaskJsonPatchRepresentation(payload);

        TaskStartedEvent taskStartedEvent = new TaskStartedEvent(new TaskId(taskId));
        TaskStartedEventHandler taskStartedEventHandler = new TaskStartedHandler();

        return taskStartedEventHandler.handleTaskStarted(taskStartedEvent);
    }
}
