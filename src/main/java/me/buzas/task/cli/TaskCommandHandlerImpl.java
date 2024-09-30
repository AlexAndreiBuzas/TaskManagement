package me.buzas.task.cli;

import me.buzas.task.abstraction.cli.TaskCommandHandler;
import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TaskCommandHandlerImpl implements TaskCommandHandler {
    private final ProjectManager projectManager;

    public TaskCommandHandlerImpl(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public void addTask(Scanner scanner) {
        System.out.print("Enter project name to assign task: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter task ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter task name: ");
        String name = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter task priority (0-10): ");
        int priority = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter task status: ");
        String status = scanner.nextLine();

        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dueDateStr = scanner.nextLine();
        Date dueDate = null;
        try {
            dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
            return;
        }

        try {
            Task task = new Task(id, name, description, priority, status, dueDate);
            projectManager.addTaskToProject(projectName, task);
            System.out.println("Task '" + name + "' added to project '" + projectName + "'.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void assignTaskToUser(Scanner scanner) {
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter task ID: ");
        int taskId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter user ID to assign task to: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        try {
            projectManager.assignTaskToUser(projectName, taskId, userId);
            System.out.println("Task assigned to user.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void removeTask(Scanner scanner) {
        System.out.print("Enter project name to remove task from: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter task ID to remove: ");
        int taskId = scanner.nextInt();
        scanner.nextLine();

        try {
            projectManager.removeTaskFromProject(projectName, taskId);
            System.out.println("Task removed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}