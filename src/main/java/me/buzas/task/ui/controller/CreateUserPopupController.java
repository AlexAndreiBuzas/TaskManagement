package me.buzas.task.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.buzas.task.model.Project;
import me.buzas.task.model.User;
import me.buzas.task.services.ProjectService;
import me.buzas.task.services.UserService;

public class CreateUserPopupController {

    @FXML
    private TextField usernameField;

    @FXML
    public TextField passwordField;

    @FXML
    private TextField teamField;

    @FXML
    private TextField positionInsideTeamField;

    private ProjectService projectService;
    private UserService userService;
    private Project selectedProject;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSelectedProject(Project project) {
        this.selectedProject = project;
    }

    @FXML
    public void handleCreateUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String team = teamField.getText();
        String positionInsideTeam = positionInsideTeamField.getText();

        if (username.isEmpty() || team.isEmpty() || positionInsideTeam.isEmpty()) {
            showAlert("Validation Error", "All fields must be filled.");
            return;
        }

        int newUserId = generateUniqueUserId();

        User newUser = new User(newUserId, username, password, team, positionInsideTeam);
        userService.addUser(newUser);
        projectService.addUserToProject(selectedProject.getName(), newUser);

        closePopup();
    }

    @FXML
    public void handleCancel() {
        closePopup();
    }

    private void closePopup() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int generateUniqueUserId() {
        return selectedProject.getUsers().keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
    }
}