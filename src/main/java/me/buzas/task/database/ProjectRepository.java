package me.buzas.task.database;

import me.buzas.task.abstraction.data.ProjectDataAccess;
import me.buzas.task.model.Project;

import java.util.List;

public class ProjectRepository implements ProjectDataAccess {
    @Override
    public void saveProject(Project project) {

    }

    @Override
    public Project loadProject(String projectName) {
        return null;
    }

    @Override
    public void updateProject(Project project) {

    }

    @Override
    public void deleteProject(String projectName) {

    }

    @Override
    public List<Project> loadAllProjects() {
        return List.of();
    }
}
