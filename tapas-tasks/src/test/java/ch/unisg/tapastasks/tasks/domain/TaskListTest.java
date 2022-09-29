package ch.unisg.tapastasks.tasks.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


public class TaskListTest {

    @Test
    void addNewTaskToTaskListSuccess() {
        TaskList taskList = TaskList.getTapasTaskList();
        taskList.getListOfTasks().getValue().clear();
        Task newTask = taskList.addNewTaskWithNameAndType(new Task.TaskName("My-Test-Task"),
            new Task.TaskType("My-Test-Type"));

        assertThat(newTask.getTaskName().getValue()).isEqualTo("My-Test-Task");
        assertThat(taskList.getListOfTasks().getValue()).hasSize(1);
        assertThat(taskList.getListOfTasks().getValue().get(0)).isEqualTo(newTask);

    }

    @Test
    void retrieveTaskSuccess() {
        TaskList taskList = TaskList.getTapasTaskList();
        Task newTask = taskList.addNewTaskWithNameAndType(new Task.TaskName("My-Test-Task2"),
            new Task.TaskType("My-Test-Type2"));

        Task retrievedTask = taskList.retrieveTaskById(newTask.getTaskId()).get();

        assertThat(retrievedTask).isEqualTo(newTask);

    }

    @Test
    void retrieveTaskFailure() {
        TaskList taskList = TaskList.getTapasTaskList();
        Task newTask = taskList.addNewTaskWithNameAndType(new Task.TaskName("My-Test-Task3"),
            new Task.TaskType("My-Test-Type3"));

        Task.TaskId fakeId = new Task.TaskId("fake-id");

        Optional<Task> retrievedTask = taskList.retrieveTaskById(fakeId);

        assertThat(retrievedTask.isPresent()).isFalse();
    }
}
