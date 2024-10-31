package me.buzas.task.manager;

import me.buzas.task.abstraction.data.ProjectDataAccess;
import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.model.User;

import java.util.List;

public class ProjectManagerImpl implements ProjectManager {

    private final ProjectDataAccess projectDataAccess;

    public ProjectManagerImpl(ProjectDataAccess projectDataAccess) {
        this.projectDataAccess = projectDataAccess;
    }

    @Override
    public void createProject(String projectName, String description) {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty.");
        }

        List<Project> allProjects = projectDataAccess.loadAllProjects();
        for (Project project : allProjects) {
            if (project.getName().equals(projectName)) {
                throw new IllegalArgumentException("Project with name '" + projectName + "' already exists.");
            }
        }

        Project newProject = new Project(projectName, description);
        projectDataAccess.saveProject(newProject);
    }

    @Override
    public Project getProject(String projectName) {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty.");
        }

        Project project = projectDataAccess.loadProject(projectName);
        if (project == null) {
            throw new IllegalArgumentException("Project with name '" + projectName + "' does not exist.");
        }

        return project;
    }

    @Override
    public void updateProject(Project project) {
        Project existingProject = projectDataAccess.loadProject(project.getName());
        if (existingProject == null) {
            throw new IllegalArgumentException("Project with name '" + project.getName() + "' does not exist.");
        }
        projectDataAccess.updateProject(project);
    }

    @Override
    public void deleteProject(String projectName) {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty.");
        }

        Project project = projectDataAccess.loadProject(projectName);
        if (project == null) {
            throw new IllegalArgumentException("Project with name '" + projectName + "' does not exist.");
        }

        projectDataAccess.deleteProject(projectName);
    }

    @Override
    public void assignTaskToUser(String projectName, int taskId, int userId) {
        Project project = getProject(projectName);
        Task task = project.getTasks().get(taskId);
        User user = project.getUsers().get(userId);

        if (task == null) {
            throw new IllegalArgumentException("Task with ID '" + taskId + "' does not exist in project '" + projectName + "'.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User with ID '" + userId + "' does not exist in project '" + projectName + "'.");
        }

        task.assignUser(user.getId());
        updateProject(project);
    }

    @Override
    public void addTaskToProject(String projectName, Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        Project project = getProject(projectName);
        project.addTask(task);
        updateProject(project);
    }

    @Override
    public Task getTaskFromProject(String projectName, int taskId) {
        Project project = getProject(projectName);
        Task task = project.getTasks().get(taskId);

        if (task == null) {
            throw new IllegalArgumentException("Task with ID '" + taskId + "' does not exist in project '" + projectName + "'.");
        }

        return task;
    }

    @Override
    public void removeTaskFromProject(String projectName, int taskId) {
        Project project = getProject(projectName);
        Task task = project.getTasks().remove(taskId);

        if (task == null) {
            throw new IllegalArgumentException("Task with ID '" + taskId + "' does not exist in project '" + projectName + "'.");
        }

        updateProject(project);
    }

    @Override
    public void addUserToProject(String projectName, User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        Project project = getProject(projectName);
        project.addUser(user);
        updateProject(project);
    }

    @Override
    public void removeUserFromProject(String projectName, int userId) {
        Project project = getProject(projectName);
        User user = project.getUsers().remove(userId);

        if (user == null) {
            throw new IllegalArgumentException("User with ID '" + userId + "' does not exist in project '" + projectName + "'.");
        }

        updateProject(project);
    }

    @Override
    public List<Project> loadAllProjects() {
        return projectDataAccess.loadAllProjects();
    }
}
