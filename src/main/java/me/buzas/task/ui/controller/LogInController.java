package me.buzas.task.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Stage loginStage;
    private MainController mainController;

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleLogin() {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();

        if (username.equals("admin") && password.equals("password")) {
            loginStage.close();
            mainController.showMainView();
        } else {
            System.out.println("Invalid username or password");
        }
    }
}
