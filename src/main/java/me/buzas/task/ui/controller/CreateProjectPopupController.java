package me.buzas.task.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.buzas.task.services.ProjectService;

public class CreateProjectPopupController {
    @FXML
    public TextField projectDescriptionField;
    @FXML
    private TextField projectNameField;

    private ProjectService projectService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @FXML
    private void handleCreateProject() {
        String projectName = projectNameField.getText();
        String projectDescription = projectDescriptionField.getText();

        if (projectName == null || projectName.trim().isEmpty()) {
            System.err.println("Project name cannot be empty.");
            showAlert("Error", "Project name cannot be empty.");
            return;
        }

        if (projectService != null) {
            projectService.createProject(projectName, projectDescription);
        } else {
            System.err.println("ProjectService is not initialized.");
        }

        closePopup();
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        stage.close();
    }

    private void closePopup() {
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}