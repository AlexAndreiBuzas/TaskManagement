package me.buzas.task.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.buzas.task.repository.ProjectRepository;
import me.buzas.task.repository.TaskRepository;
import me.buzas.task.repository.UserRepository;
import me.buzas.task.services.ProjectService;
import me.buzas.task.services.TaskService;
import me.buzas.task.services.UserService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class MainController {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_management", "root", "taskproject");

    ProjectRepository projectRepository = new ProjectRepository(connection);
    TaskRepository taskRepository = new TaskRepository(connection);
    UserRepository userRepository = new UserRepository(connection);

    ProjectService projectService = new ProjectService(projectRepository, taskRepository);
    TaskService taskService = new TaskService(taskRepository, projectRepository);
    UserService userService = new UserService(userRepository);

    @FXML
    private Pane contentArea;

    private Stage primaryStage;

    public MainController() throws SQLException {
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showMainView() {
        try {
            Parent mainView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/me/buzas/task/fxml/MainView.fxml")));
            Scene mainScene = new Scene(mainView);
            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Failed to load MainView");
        }
    }

    @FXML
    public void goToProjects() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/me/buzas/task/fxml/ProjectView.fxml")));
            Parent projectView = loader.load();

            ProjectController projectController = loader.getController();
            projectController.setProjectService(projectService);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(projectView);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading ProjectView.fxml");
        }
    }

    @FXML
    public void goToTasks() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/me/buzas/task/fxml/TaskView.fxml")));
            Parent taskView = loader.load();

            TaskController taskController = loader.getController();
            taskController.setProjectService(projectService);
            taskController.setTaskService(taskService);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(taskView);
        } catch (IOException e) {
            System.out.println("Error loading TaskView.fxml");
        }
    }

    @FXML
    public void goToUsers() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/me/buzas/task/fxml/UserView.fxml")));
            Parent userView = loader.load();

            UserController userController = loader.getController();
            userController.setUserService(userService);
            userController.setProjectService(projectService);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(userView);
        } catch (IOException e) {
            System.out.println("Error loading UserView.fxml");
        }
    }

    @FXML
    public void goToReports() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/me/buzas/task/fxml/ReportView.fxml")));
            Parent reportView = loader.load();

            ReportController reportController = loader.getController();
            reportController.setProjectService(projectService);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(reportView);
        } catch (IOException e) {
            System.out.println("Error loading ReportView.fxml");
        }
    }
}