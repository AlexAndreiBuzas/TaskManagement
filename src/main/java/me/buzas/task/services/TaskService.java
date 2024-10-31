package me.buzas.task.services;

import me.buzas.task.abstraction.manager.TaskManager;
import me.buzas.task.model.Task;
import me.buzas.task.repository.ProjectRepository;
import me.buzas.task.repository.TaskRepository;

public class TaskService implements TaskManager {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void addTask(Task task) {
        taskRepository.saveTask(task);
    }

    @Override
    public Task getTask(int id) {
        return taskRepository.loadTask(id);
    }

    @Override
    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    @Override
    public void removeTask(int id) {
        taskRepository.deleteTask(id);
    }
}
