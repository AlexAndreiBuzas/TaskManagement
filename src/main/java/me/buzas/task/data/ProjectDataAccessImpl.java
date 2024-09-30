package me.buzas.task.data;

import me.buzas.task.abstraction.data.ProjectDataAccess;
import me.buzas.task.model.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectDataAccessImpl implements ProjectDataAccess {

    private final Map<String, Project> projects = new HashMap<>();

    @Override
    public void saveProject(Project project) {
        projects.put(project.getName(), project);
    }

    @Override
    public Project loadProject(String projectName) {
        return projects.get(projectName);
    }

    @Override
    public void updateProject(Project project) {
        projects.put(project.getName(), project);
    }

    @Override
    public void deleteProject(String projectName) {
        projects.remove(projectName);
    }

    @Override
    public List<Project> loadAllProjects() {
        return new ArrayList<>(projects.values());
    }
}
