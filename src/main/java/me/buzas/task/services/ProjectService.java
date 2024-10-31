package me.buzas.task.services;

import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.model.User;
import me.buzas.task.repository.ProjectRepository;
import me.buzas.task.repository.TaskRepository;

import java.util.List;

public class ProjectService implements ProjectManager {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void createProject(String projectName, String description) {
        Project project = new Project(projectName, description);
        projectRepository.saveProject(project);
    }

    @Override
    public Project getProject(String projectName) {
        return projectRepository.loadProject(projectName);
    }

    @Override
    public void updateProject(Project project) {
        projectRepository.updateProject(project);
    }

    @Override
    public void deleteProject(String projectName) {
        projectRepository.deleteProject(projectName);
    }

    @Override
    public void assignTaskToUser(String projectName, int taskId, int userId) {
        Project project = projectRepository.loadProject(projectName);
        if (project != null) {
            Task task = project.getTasks().get(taskId);
            if (task != null) {
                task.assignUser(userId);
                projectRepository.updateProject(project);
            }
        }
    }

    public void saveProjectTasks(Project project) {
        for (Task task : project.getTasks().values()) {
            taskRepository.saveTask(task);
        }
    }

    @Override
    public void addTaskToProject(String projectName, Task task) {
        Project project = projectRepository.loadProject(projectName);
        if (project != null) {
            project.addTask(task);
            taskRepository.saveTask(task);
            projectRepository.updateProject(project);
        }
    }

    @Override
    public Task getTaskFromProject(String projectName, int taskId) {
        Project project = projectRepository.loadProject(projectName);
        return project != null ? project.getTasks().get(taskId) : null;
    }

    @Override
    public void removeTaskFromProject(String projectName, int taskId) {
        Project project = projectRepository.loadProject(projectName);
        if (project != null) {
            project.getTasks().remove(taskId);
            projectRepository.updateProject(project);
        }
    }

    @Override
    public void addUserToProject(String projectName, User user) {
        Project project = projectRepository.loadProject(projectName);
        if (project != null) {
            project.addUser(user);
            projectRepository.updateProject(project);
        }
    }

    @Override
    public void removeUserFromProject(String projectName, int userId) {
        Project project = projectRepository.loadProject(projectName);
        if (project != null) {
            project.getUsers().remove(userId);
            projectRepository.updateProject(project);
        }
    }

    public List<Project> loadAllProjects() {
        return projectRepository.loadAllProjects();
    }
}
