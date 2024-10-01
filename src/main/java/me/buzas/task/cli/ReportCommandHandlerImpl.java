package me.buzas.task.cli;

import me.buzas.task.abstraction.cli.ReportCommandHandler;
import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ReportCommandHandlerImpl implements ReportCommandHandler {
    private final ProjectManager projectManager;

    public ReportCommandHandlerImpl(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public void viewReport(Scanner scanner) {
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();

        try {
            Project project = projectManager.getProject(projectName);
            if (project == null) {
                System.out.println("Error: Project with name '" + projectName + "' does not exist.");
                return;
            }

            System.out.println("\n--- Project Report ---");
            System.out.println("Project Name: " + project.getName());
            System.out.println("Description: " + project.getDescription());
            System.out.println("Tasks: ");
            for (Task task : project.getTasks().values()) {
                System.out.println("  - Task ID: " + task.getId());
                System.out.println("    Name: " + task.getName());
                System.out.println("    Description: " + task.getDescription());
                System.out.println("    Priority: " + task.getPriority());
                System.out.println("    Status: " + task.getStatus());
            }

            System.out.println("Users: ");
            for (User user : project.getUsers().values()) {
                System.out.println("  - User ID: " + user.getId());
                System.out.println("    Username: " + user.getUsername());
                System.out.println("    Team: " + user.getTeam());
                System.out.println("    Position: " + user.getPositionInsideTeam());

                List<Task> assignedTasks = project.getTasksAssignedToUser(user.getId());
                if (assignedTasks.isEmpty()) {
                    System.out.println("    Assigned Tasks: None");
                } else {
                    System.out.println("    Assigned Tasks: ");
                    for (Task assignedTask : assignedTasks) {
                        System.out.println("      - Task ID: " + assignedTask.getId() + " (" + assignedTask.getName() + ")");
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while viewing the project report: " + e.getMessage());
        }
    }

    @Override
    public void exportReport(Scanner scanner) {
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter file name for export: ");
        String fileName = scanner.nextLine();

        try {
            Project project = projectManager.getProject(projectName);
            if (project == null) {
                System.out.println("Error: Project with name '" + projectName + "' does not exist.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("--- Project Report ---\n");
                writer.write("Project Name: " + project.getName() + "\n");
                writer.write("Description: " + project.getDescription() + "\n");
                writer.write("Tasks: \n");

                for (Task task : project.getTasks().values()) {
                    writer.write("  - Task ID: " + task.getId() + "\n");
                    writer.write("    Name: " + task.getName() + "\n");
                    writer.write("    Description: " + task.getDescription() + "\n");
                    writer.write("    Priority: " + task.getPriority() + "\n");
                    writer.write("    Status: " + task.getStatus() + "\n");
                }

                writer.write("Users: \n");
                for (User user : project.getUsers().values()) {
                    writer.write("  - User ID: " + user.getId() + "\n");
                    writer.write("    Username: " + user.getUsername() + "\n");
                    writer.write("    Team: " + user.getTeam() + "\n");
                    writer.write("    Position: " + user.getPositionInsideTeam() + "\n");

                    List<Task> assignedTasks = project.getTasksAssignedToUser(user.getId());
                    if (assignedTasks.isEmpty()) {
                        writer.write("    Assigned Tasks: None\n");
                    } else {
                        writer.write("    Assigned Tasks: \n");
                        for (Task assignedTask : assignedTasks) {
                            writer.write("      - Task ID: " + assignedTask.getId() + " (" + assignedTask.getName() + ")\n");
                        }
                    }
                }

                System.out.println("Report exported successfully to " + fileName);
            }
        } catch (IOException e) {
            System.out.println("Error exporting report: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while exporting the report: " + e.getMessage());
        }
    }
}