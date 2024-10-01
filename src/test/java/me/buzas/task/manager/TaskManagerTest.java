package me.buzas.task.manager;

import me.buzas.task.abstraction.data.TaskDataAccess;
import me.buzas.task.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskManagerTest {
    @Mock
    private TaskDataAccess taskDataAccess;

    @InjectMocks
    private TaskManagerImpl taskManager;

    @Test
    public void testAddTask() {
        Task task = new Task(1, "Task 1", "Description 1", 5, "Pending", new Date());

        taskManager.addTask(task);

        verify(taskDataAccess).saveTask(task);
    }

    @Test
    public void testAddTask_NullTask() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                taskManager.addTask(null)
        );
        assertEquals("Task cannot be null.", exception.getMessage());
    }

    @Test
    public void testGetTask() {
        Task expectedTask = new Task(1, "Task 1", "Description 1", 5, "Pending", new Date());

        when(taskDataAccess.loadTask(1)).thenReturn(expectedTask);

        Task actualTask = taskManager.getTask(1);

        assertNotNull(actualTask);
        assertEquals(expectedTask.getId(), actualTask.getId());
        verify(taskDataAccess).loadTask(1);
    }

    @Test
    public void testGetTask_InvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                taskManager.getTask(-1)
        );
        assertEquals("Task ID must be greater than 0.", exception.getMessage());
    }

    @Test
    public void testGetTask_NonExistentTask() {
        when(taskDataAccess.loadTask(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                taskManager.getTask(1)
        );
        assertEquals("Task with ID '1' does not exist.", exception.getMessage());
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task(1, "Task 1", "Description 1", 5, "Pending", new Date());
        task.setDescription("Updated Description");

        when(taskDataAccess.loadTask(1)).thenReturn(task);

        taskManager.updateTask(task);

        verify(taskDataAccess).updateTask(task);
    }

    @Test
    public void testUpdateTask_NonExistentTask() {
        Task task = new Task(1, "Task 1", "Description 1", 5, "Pending", new Date());

        when(taskDataAccess.loadTask(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                taskManager.updateTask(task)
        );
        assertEquals("Task with ID '1' does not exist.", exception.getMessage());
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task(1, "Task 1", "Description 1", 5, "Pending", new Date());

        when(taskDataAccess.loadTask(1)).thenReturn(task);

        taskManager.removeTask(1);

        verify(taskDataAccess).deleteTask(1);
    }

    @Test
    public void testDeleteTask_InvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                taskManager.removeTask(0)
        );
        assertEquals("Task ID must be greater than 0.", exception.getMessage());
    }

    @Test
    public void testDeleteTask_NonExistentTask() {
        when(taskDataAccess.loadTask(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                taskManager.removeTask(1)
        );
        assertEquals("Task with ID '1' does not exist.", exception.getMessage());
    }
}