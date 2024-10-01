package me.buzas.task;

import me.buzas.task.abstraction.cli.TaskManagerCommandHandler;
import me.buzas.task.abstraction.data.ProjectDataAccess;
import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.cli.*;
import me.buzas.task.data.ProjectDataAccessImpl;
import me.buzas.task.manager.ProjectManagerImpl;

public class Main {
    public static void main(String[] args) {
        ProjectDataAccess projectDataAccess = new ProjectDataAccessImpl();
        ProjectManager projectManager = new ProjectManagerImpl(projectDataAccess);

        ProjectCommandHandlerImpl projectHandler = new ProjectCommandHandlerImpl(projectManager);
        TaskCommandHandlerImpl taskHandler = new TaskCommandHandlerImpl(projectManager);
        UserCommandHandlerImpl userHandler = new UserCommandHandlerImpl(projectManager);
        ReportCommandHandlerImpl reportHandler = new ReportCommandHandlerImpl(projectManager);

        TaskManagerCommandHandler managerHandler = new TaskManagerCommandHandlerImpl(projectHandler, taskHandler, userHandler, reportHandler);

        managerHandler.runMenu();
    }
}
