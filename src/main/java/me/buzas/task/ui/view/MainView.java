package me.buzas.task.ui.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.abstraction.manager.TaskManager;
import me.buzas.task.abstraction.manager.UserManager;
import me.buzas.task.repository.ProjectRepository;
import me.buzas.task.repository.TaskRepository;
import me.buzas.task.repository.UserRepository;
import me.buzas.task.services.ProjectService;
import me.buzas.task.services.TaskService;
import me.buzas.task.services.UserService;
import me.buzas.task.ui.controller.LogInController;
import me.buzas.task.ui.controller.MainController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Connection connection = getConnection();

            dependencyInjection(connection);

            MainController mainController = getMainController(primaryStage);

            Stage loginStage = getLoginStage(mainController);

            switchStage(loginStage, mainController);
        } catch (IOException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/task_management", "root", "taskproject");
    }

    private static void dependencyInjection(Connection connection) {
        ProjectRepository projectRepository = new ProjectRepository(connection);
        TaskRepository taskRepository = new TaskRepository(connection);
        UserRepository userRepository = new UserRepository(connection);

        ProjectManager projectService = new ProjectService(projectRepository, taskRepository);
        TaskManager taskService = new TaskService(taskRepository, projectRepository);
        UserManager userService = new UserService(userRepository);
    }

    private MainController getMainController(Stage primaryStage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/me/buzas/task/fxml/MainView.fxml"));
        Parent mainRoot = mainLoader.load();
        MainController mainController = mainLoader.getController();
        mainController.setPrimaryStage(primaryStage);
        return mainController;
    }

    private Stage getLoginStage(MainController mainController) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/me/buzas/task/fxml/LogInView.fxml"));
        Parent loginRoot = loginLoader.load();
        LogInController logInController = loginLoader.getController();

        logInController.setMainController(mainController);

        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(loginRoot));
        logInController.setLoginStage(loginStage);

        loginStage.setOnCloseRequest(event -> {
            Platform.exit();
        });
        return loginStage;
    }

    private static void switchStage(Stage loginStage, MainController mainController) {
        loginStage.showAndWait();

        if (loginStage.isShowing()) {
            return;
        }

        mainController.showMainView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
