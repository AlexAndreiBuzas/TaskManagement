package me.buzas.task.manager;

import me.buzas.task.abstraction.data.TaskDataAccess;
import me.buzas.task.abstraction.manager.TaskManager;
import me.buzas.task.model.Task;

public class TaskManagerImpl implements TaskManager {

    private final TaskDataAccess taskDataAccess;

    public TaskManagerImpl(TaskDataAccess taskDataAccess) {
        this.taskDataAccess = taskDataAccess;
    }

    @Override
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        taskDataAccess.saveTask(task);
    }

    @Override
    public Task getTask(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be greater than 0.");
        }

        Task task = taskDataAccess.loadTask(id);
        if (task == null) {
            throw new IllegalArgumentException("Task with ID '" + id + "' does not exist.");
        }
        return task;
    }

    @Override
    public void updateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        Task existingTask = taskDataAccess.loadTask(task.getId());
        if (existingTask == null) {
            throw new IllegalArgumentException("Task with ID '" + task.getId() + "' does not exist.");
        }

        taskDataAccess.updateTask(task);
    }

    @Override
    public void removeTask(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be greater than 0.");
        }

        Task task = taskDataAccess.loadTask(id);
        if (task == null) {
            throw new IllegalArgumentException("Task with ID '" + id + "' does not exist.");
        }

        taskDataAccess.deleteTask(id);
    }
}
