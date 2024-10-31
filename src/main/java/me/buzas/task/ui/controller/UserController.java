package me.buzas.task.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.buzas.task.model.Project;
import me.buzas.task.model.User;
import me.buzas.task.services.ProjectService;
import me.buzas.task.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserController {

    @FXML
    private ComboBox<Project> projectComboBox;

    @FXML
    private VBox usersContainer;

    @FXML
    private Label noUserLabel;

    private ProjectService projectService;
    private UserService userService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
        loadProjects();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void initialize() {
        if (projectService == null) {
            System.err.println("ProjectService is not initialized. (User)");
        }
    }

    private void loadProjects() {
        List<Project> projects = projectService.loadAllProjects();
        System.out.println("Number of projects loaded: " + (projects != null ? projects.size() : "null"));

        projectComboBox.getItems().clear();
        if (projects != null && !projects.isEmpty()) {
            projectComboBox.getItems().addAll(projects);
        } else {
            System.err.println("No projects available to display.");
            showAlert("No Projects", "No projects are available. Please add a project.");
        }
    }

    @FXML
    public void onProjectSelected() {
        Project selectedProject = projectComboBox.getSelectionModel().getSelectedItem();
        loadUsers(selectedProject);
    }

    private void loadUsers(Project project) {
        if (project == null) {
            usersContainer.getChildren().clear();
            noUserLabel.setVisible(true);
            return;
        }

        Map<Integer, User> users = project.getUsers();
        System.out.println("Users loaded for project: " + (users != null ? users.size() : "null"));

        usersContainer.getChildren().clear();


        if (users == null || users.isEmpty()) {
            noUserLabel.setVisible(true);
            System.out.println("No users found.");
        } else {
            noUserLabel.setVisible(false);
            for (User user : users.values()) {
                Label userLabel = new Label(user.getUsername() + " - " + user.getPositionInsideTeam());

                Button editButton = new Button("Edit");
                editButton.setOnAction(e -> editUser(user));

                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(e -> deleteUser(user));

                VBox userBox = new VBox(userLabel, editButton, deleteButton);
                usersContainer.getChildren().add(userBox);
            }
        }
    }

    private void editUser(User user) {
        // Logic to open a popup to edit user details
    }

    private void deleteUser(User user) {
        userService.removeUser(user.getId());
        onProjectSelected();
    }

    @FXML
    public void openCreateUserPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/me/buzas/task/fxml/CreateUserPopup.fxml"));

            Parent popupContent = loader.load();

            CreateUserPopupController popupController = loader.getController();
            popupController.setProjectService(projectService);
            popupController.setUserService(userService);
            popupController.setSelectedProject(projectComboBox.getSelectionModel().getSelectedItem());

            Stage popupStage = new Stage();
            popupStage.setTitle("Create New User");
            popupStage.setScene(new Scene(popupContent));
            popupStage.initOwner(projectComboBox.getScene().getWindow());
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.showAndWait();

            loadUsers(projectComboBox.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the Create User popup.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}