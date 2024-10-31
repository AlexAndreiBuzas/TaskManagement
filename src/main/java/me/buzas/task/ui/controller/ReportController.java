package me.buzas.task.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.model.User;
import me.buzas.task.services.ProjectService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportController {

    @FXML
    private ComboBox<String> projectComboBox;
    @FXML
    private TextArea reportTextArea;

    private ProjectService projectService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
        loadProjects();
    }

    @FXML
    public void initialize() {
        loadProjects();
    }

    private void loadProjects() {
        if (projectService == null) {
            System.err.println("ProjectService is not initialized!");
            return;
        }

        List<Project> projects = projectService.loadAllProjects();
        System.out.println("Number of projects loaded: " + (projects != null ? projects.size() : "null"));

        projectComboBox.getItems().clear();
        if (projects != null && !projects.isEmpty()) {
            projectComboBox.getItems().addAll(projects.stream().map(Project::getName).toList());
        } else {
            System.err.println("No projects available to display.");
            showAlert("No Projects", "No projects are available. Please add a project.");
        }
    }

    @FXML
    public void generateReport() {
        String projectName = projectComboBox.getSelectionModel().getSelectedItem();
        if (projectName == null || projectName.isEmpty()) {
            showAlert("Error", "Please select a project to view the report.");
            return;
        }

        Project project = projectService.getProject(projectName);
        if (project == null) {
            showAlert("Error", "Project not found.");
            return;
        }

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("--- Project Report ---\n");
        reportBuilder.append("Project Name: ").append(project.getName()).append("\n");
        reportBuilder.append("Description: ").append(project.getDescription()).append("\n\n");

        reportBuilder.append("Tasks:\n");
        for (Task task : project.getTasks().values()) {
            reportBuilder.append("  - Task ID: ").append(task.getId()).append("\n");
            reportBuilder.append("    Name: ").append(task.getName()).append("\n");
            reportBuilder.append("    Description: ").append(task.getDescription()).append("\n");
            reportBuilder.append("    Priority: ").append(task.getPriority()).append("\n");
            reportBuilder.append("    Status: ").append(task.getStatus()).append("\n\n");
        }

        reportBuilder.append("Users:\n");
        for (User user : project.getUsers().values()) {
            reportBuilder.append("  - User ID: ").append(user.getId()).append("\n");
            reportBuilder.append("    Username: ").append(user.getUsername()).append("\n");
            reportBuilder.append("    Team: ").append(user.getTeam()).append("\n");
            reportBuilder.append("    Position: ").append(user.getPositionInsideTeam()).append("\n");

            var assignedTasks = project.getTasksAssignedToUser(user.getId());
            if (assignedTasks.isEmpty()) {
                reportBuilder.append("    Assigned Tasks: None\n");
            } else {
                reportBuilder.append("    Assigned Tasks:\n");
                for (Task assignedTask : assignedTasks) {
                    reportBuilder.append("      - Task ID: ").append(assignedTask.getId())
                            .append(" (").append(assignedTask.getName()).append(")\n");
                }
            }
            reportBuilder.append("\n");
        }

        reportTextArea.setText(reportBuilder.toString());
    }

    @FXML
    public void exportReport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showSaveDialog(reportTextArea.getScene().getWindow());
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(reportTextArea.getText());
                showAlert("Success", "Report exported successfully to " + file.getAbsolutePath());
            } catch (IOException e) {
                showAlert("Error", "Failed to export report: " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}