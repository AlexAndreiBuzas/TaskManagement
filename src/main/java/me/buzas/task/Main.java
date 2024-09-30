package me.buzas.task;

import me.buzas.task.abstraction.cli.TaskManagerCommandHandler;
import me.buzas.task.abstraction.data.ProjectDataAccess;
import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.cli.*;
import me.buzas.task.data.ProjectDataAccessImpl;
import me.buzas.task.manager.ProjectManagerImpl;
import me.buzas.task.model.Project;

public class Main {
    public static void main(String[] args) {
        Project project = new Project("Example Project", "This is a sample project.");
        ProjectDataAccess projectDataAccess = new ProjectDataAccessImpl();
        ProjectManager projectManager = new ProjectManagerImpl(projectDataAccess);

        ProjectCommandHandlerImpl projectHandler = new ProjectCommandHandlerImpl(projectManager);
        TaskCommandHandlerImpl taskHandler = new TaskCommandHandlerImpl(projectManager);
        UserCommandHandlerImpl userHandler = new UserCommandHandlerImpl(projectManager);
        ReportCommandHandlerImpl reportHandler = new ReportCommandHandlerImpl();

        TaskManagerCommandHandler managerHandler = new TaskManagerCommandHandlerImpl(projectHandler, taskHandler, userHandler, reportHandler, projectManager);

        managerHandler.runMenu();
    }
}
