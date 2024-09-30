package me.buzas.task.cli;

import me.buzas.task.abstraction.cli.ProjectCommandHandler;
import me.buzas.task.abstraction.manager.ProjectManager;

import java.util.Scanner;

public class ProjectCommandHandlerImpl implements ProjectCommandHandler {
    private final ProjectManager projectManager;

    public ProjectCommandHandlerImpl(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public void createProject(Scanner scanner) {
        System.out.print("Enter project name: ");
        String name = scanner.nextLine();
        System.out.print("Enter project description: ");
        String description = scanner.nextLine();

        try {
            projectManager.createProject(name, description);
            System.out.println("Project: " + name + " created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
