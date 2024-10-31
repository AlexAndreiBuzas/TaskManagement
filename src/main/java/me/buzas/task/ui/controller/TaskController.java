package me.buzas.task.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.services.ProjectService;
import me.buzas.task.services.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TaskController {
    @FXML
    public VBox tasksContainer;

    @FXML
    public ComboBox<Project> projectComboBox;

    @FXML
    public Label noTasksLabel;

    private ProjectService projectService;
    private TaskService taskService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
        loadProjects();
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @FXML
    public void initialize() {
        if (projectService == null) {
            System.err.println("ProjectService is not initialized. (Task)");
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
        Project selectedProject = projectComboBox.getValue();
        if (selectedProject != null) {
            selectedProject = projectService.getProject(selectedProject.getName());
            loadTasksForSelectedProject(selectedProject);
        } else {
            System.err.println("No project selected.");
        }
    }

    private void loadTasksForSelectedProject(Project project) {
        tasksContainer.getChildren().clear();

        Map<Integer, Task> tasks = project.getTasks();
        System.out.printf("Number of tasks loaded for project '%s': %d%n", project.getName(), tasks.size());

        if (tasks.isEmpty()) {
            noTasksLabel.setVisible(true);
            System.out.println("No tasks found.");
        } else {
            noTasksLabel.setVisible(false);

            for (Task task : tasks.values()) {
                HBox taskBox = createTaskBox(task);
                tasksContainer.getChildren().add(taskBox);
            }
        }
    }

    private HBox createTaskBox(Task task) {
        Label taskLabel = new Label(task.getName());
        taskLabel.setStyle("-fx-font-size: 14px;  -fx-font-weight: bold;");

        Label taskDescription = new Label(task.getDescription());
        taskDescription.setStyle("-fx-font-size: 12px;");

        VBox textContainer = new VBox(taskLabel, taskDescription);
        textContainer.setSpacing(5);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editTask(task));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteTask(task));

        HBox taskBox = new HBox(textContainer, spacer, editButton, deleteButton);
        taskBox.setSpacing(10);
        taskBox.setPrefWidth(Double.MAX_VALUE);
        return taskBox;
    }

    @FXML
    public void openCreateTaskPopup() {
        Project selectedProject = projectComboBox.getValue();
        if (selectedProject == null) {
            showAlert("No Project Selected", "Please select a project before creating a task.");
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/me/buzas/task/fxml/CreateTaskPopup.fxml"));
            Parent popupContent = fxmlLoader.load();

            CreateTaskPopupController popupController = fxmlLoader.getController();
            popupController.setProject(selectedProject);
            popupController.setProjectService(projectService);

            Stage popupStage = new Stage();
            popupStage.setTitle("Create New Task");
            popupStage.setScene(new Scene(popupContent));
            popupStage.initOwner(projectComboBox.getScene().getWindow());
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.showAndWait();

            loadTasksForSelectedProject(selectedProject);
        } catch (IOException e) {
            showAlert("Error", "Failed to open the Create Task popup.");
        }
    }

    private void editTask(Task task) {
        // Logic to open an edit popup editor

    }

    private void deleteTask(Task task) {
        Project selectedProject = projectComboBox.getValue();
        if (selectedProject != null) {
            selectedProject.getTasks().remove(task.getId());
            taskService.removeTask(task.getId());
            projectService.updateProject(selectedProject);
            loadTasksForSelectedProject(selectedProject);
            showAlert("Success", "Task deleted successfully.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}