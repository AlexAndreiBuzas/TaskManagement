package me.buzas.task.cli;

import me.buzas.task.abstraction.cli.UserCommandHandler;
import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.model.User;

import java.util.Scanner;

public class UserCommandHandlerImpl implements UserCommandHandler {
    private final ProjectManager projectManager;

    public UserCommandHandlerImpl(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public void addUser(Scanner scanner) {
        System.out.print("Enter project name to assign user: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter user ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter team name: ");
        String team = scanner.nextLine();

        System.out.print("Enter position in team: ");
        String position = scanner.nextLine();

        try {
            User user = new User(id, username, team, position);
            projectManager.addUserToProject(projectName, user);
            System.out.println("User '" + username + "' added to project '" + projectName + "'.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void removeUser(Scanner scanner) {
        System.out.print("Enter project name to remove user from: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter user ID to remove: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        try {
            projectManager.removeUserFromProject(projectName, userId);
            System.out.println("User with ID " + userId + " removed from project '" + projectName + "'.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}