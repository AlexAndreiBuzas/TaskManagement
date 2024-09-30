package me.buzas.task.manager;

import me.buzas.task.abstraction.data.TaskDataAccess;
import me.buzas.task.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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

        verify(taskDataAccess, times(1)).saveTask(task);
    }

    @Test
    public void testGetTask() {
        Task expectedTask = new Task(1, "Task 1", "Description 1", 5, "Pending", new Date());

        when(taskDataAccess.loadTask(1)).thenReturn(expectedTask);

        Task actualTask = taskManager.getTask(1);

        assertNotNull(actualTask);
        assertEquals(expectedTask.getId(), actualTask.getId());
        verify(taskDataAccess, times(1)).loadTask(1);
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task(1, "Task 1", "Description 1", 5, "Pending", new Date());
        task.setDescription("Updated Description");

        taskManager.updateTask(task);

        verify(taskDataAccess, times(1)).updateTask(task);
    }

    @Test
    public void testDeleteTask() {
        int taskId = 1;

        taskManager.removeTask(taskId);

        verify(taskDataAccess, times(1)).deleteTask(taskId);
    }
}