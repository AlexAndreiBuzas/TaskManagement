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
        taskDataAccess.saveTask(task);
    }

    @Override
    public Task getTask(int id) {
        return taskDataAccess.loadTask(id);
    }

    @Override
    public void updateTask(Task task) {
        taskDataAccess.updateTask(task);
    }

    @Override
    public void removeTask(int id) {
        taskDataAccess.deleteTask(id);
    }
}
