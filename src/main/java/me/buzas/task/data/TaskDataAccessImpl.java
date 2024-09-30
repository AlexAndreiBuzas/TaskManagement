package me.buzas.task.data;

import me.buzas.task.abstraction.data.TaskDataAccess;
import me.buzas.task.model.Task;

import java.util.HashMap;
import java.util.Map;

public class TaskDataAccessImpl implements TaskDataAccess {

    private final Map<Integer, Task> tasks = new HashMap<>();

    @Override
    public void saveTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public Task loadTask(int id) {
        return tasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }
}
