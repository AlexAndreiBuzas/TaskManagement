package me.buzas.task.abstraction.data;

import me.buzas.task.model.Task;

public interface TaskDataAccess {
    void saveTask(Task task);

    Task loadTask(int id);

    void updateTask(Task task);

    void deleteTask(int id);
}
