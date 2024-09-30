package me.buzas.task.cli;

import me.buzas.task.abstraction.cli.*;
import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.model.Project;

import java.util.Scanner;

public class TaskManagerCommandHandlerImpl implements TaskManagerCommandHandler {
    private final ProjectManager projectManager;
    private final ProjectCommandHandler projectHandler;
    private final TaskCommandHandler taskHandler;
    private final UserCommandHandler userHandler;
    private final ReportCommandHandler reportHandler;
    private final Scanner scanner;

    public TaskManagerCommandHandlerImpl(ProjectCommandHandler projectHandler, TaskCommandHandler taskHandler, UserCommandHandler userHandler, ReportCommandHandler reportHandler, ProjectManager projectManager) {
        this.projectHandler = projectHandler;
        this.taskHandler = taskHandler;
        this.userHandler = userHandler;
        this.reportHandler = reportHandler;
        this.projectManager = projectManager;
        scanner = new Scanner(System.in);
    }

    @Override
    public void runMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n--- Task Management System ---");
            System.out.println("1. Create Project");
            System.out.println("2. Add User to Project");  // Moved to 2nd position
            System.out.println("3. Add Task");  // Moved to 3rd position
            System.out.println("4. Assign Task to User");
            System.out.println("5. Remove Task");
            System.out.println("6. View Report");
            System.out.println("7. Export Report");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createProject();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    addTask();
                    break;
                case 4:
                    assignTaskToUser();
                    break;
                case 5:
                    removeTask();
                    break;
                case 6:
                    viewReport();
                    break;
                case 7:
                    exportReport();
                    break;
                case 8:
                    running = false;
                    System.out.println("Exiting Task Manager.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }

    private void createProject() {
        projectHandler.createProject(scanner);
    }

    private void addUser() {
        userHandler.addUser(scanner);
    }

    private void addTask() {
        taskHandler.addTask(scanner);
    }

    private void assignTaskToUser() {
        taskHandler.assignTaskToUser(scanner);
    }

    private void removeTask() {
        taskHandler.removeTask(scanner);
    }

    private void viewReport() {
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();

        Project project = projectManager.getProject(projectName);
        if (project == null) {
            System.out.println("Project does not exist.");
        } else {
            reportHandler.viewReport(project);
        }
    }

    private void exportReport() {
        System.out.print("Enter project name to export report: ");
        String projectName = scanner.nextLine();
        Project project = projectManager.getProject(projectName);

        if (project == null) {
            System.out.println("Project does not exist.");
            return;
        }

        System.out.print("Enter file name to save the report: ");
        String fileName = scanner.nextLine();

        reportHandler.exportReport(project, fileName);
    }
}