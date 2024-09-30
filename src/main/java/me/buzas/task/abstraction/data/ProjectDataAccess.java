package me.buzas.task.abstraction.data;

import me.buzas.task.model.Project;

import java.util.List;

public interface ProjectDataAccess {
    void saveProject(Project project);

    Project loadProject(String projectName);

    void updateProject(Project project);

    void deleteProject(String projectName);

    List<Project> loadAllProjects();
}
