package me.buzas.task.abstraction.manager;

import me.buzas.task.model.Task;

public interface TaskManager {
    void addTask(Task task);

    Task getTask(int id);

    void updateTask(Task task);

    void removeTask(int id);
}
