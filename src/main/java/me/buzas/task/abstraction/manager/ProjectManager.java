package me.buzas.task.abstraction.manager;

import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.model.User;

public interface ProjectManager {
    void createProject(String projectName, String description);

    Project getProject(String projectName);

    void updateProject(Project project);

    void deleteProject(String projectName);

    void assignTaskToUser(String projectName, int taskId, int userId);

    void addTaskToProject(String projectName, Task task);

    Task getTaskFromProject(String projectName, int taskId);

    void removeTaskFromProject(String projectName, int taskId);

    void addUserToProject(String projectName, User user);

    void removeUserFromProject(String projectName, int userId);
}
