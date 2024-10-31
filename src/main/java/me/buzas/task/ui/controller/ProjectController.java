package me.buzas.task.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.buzas.task.model.Project;
import me.buzas.task.services.ProjectService;

import java.io.IOException;
import java.util.List;

public class ProjectController {
    @FXML
    public VBox projectsContainer;

    @FXML
    public Label noProjectsLabel;

    private ProjectService projectService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
        loadProjects();
    }

    @FXML
    public void initialize() {
        if (projectService == null) {
            System.err.println("ProjectService is not initialized. Cannot load projects.(Project)");
        } else {
            loadProjects();
        }
    }

    public void loadProjects() {
        if (projectService == null) {
            System.err.println("ProjectService is not initialized. Cannot load projects.(Project)");
            return;
        }

        List<Project> projects = projectService.loadAllProjects();

        if (projects == null) {
            System.err.println("Failed to load projects: projectService returned null.(Project)");
            noProjectsLabel.setVisible(true);
            return;
        }

        if (projects.isEmpty()) {
            noProjectsLabel.setVisible(true);
        } else {
            noProjectsLabel.setVisible(false);
            projectsContainer.getChildren().clear();

            for (Project project : projects) {
                HBox projectBox = new HBox();
                projectBox.setSpacing(10);
                projectBox.setPrefWidth(Double.MAX_VALUE);

                VBox textContainer = new VBox();
                textContainer.setSpacing(5);

                Label nameLabel = new Label(project.getName());
                nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

                Label descriptionLabel = new Label(project.getDescription());
                descriptionLabel.setStyle("-fx-font-size: 12px;");

                textContainer.getChildren().addAll(nameLabel, descriptionLabel);

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                Button editButton = new Button("Edit");
                editButton.setOnAction(e -> editProject(project));

                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(e -> deleteProject(project));

                projectBox.getChildren().addAll(textContainer, spacer, editButton, deleteButton);

                projectsContainer.getChildren().add(projectBox);
            }
        }
    }

    @FXML
    public void editProject(Project project) {

    }

    @FXML
    public void deleteProject(Project project) {
        projectService.deleteProject(project.getName());
        projectsContainer.getChildren().clear();
        loadProjects();
    }

    @FXML
    public void openCreateProjectPopup(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/me/buzas/task/fxml/CreateProjectPopup.fxml"));
            Parent popupContent = fxmlLoader.load();

            CreateProjectPopupController popupController = fxmlLoader.getController();
            popupController.setProjectService(projectService);

            Stage popupStage = new Stage();
            popupStage.setTitle("Create New Project");
            popupStage.setScene(new Scene(popupContent));

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            popupStage.initOwner(currentStage);
            popupStage.initModality(Modality.WINDOW_MODAL);

            popupStage.showAndWait();

            loadProjects();
        } catch (IOException e) {
            System.err.println("Failed to load popup content.");
            showAlert("Error", "Failed to load popup content.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
