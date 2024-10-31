package me.buzas.task.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.services.ProjectService;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.IntStream;

public class CreateTaskPopupController {
    @FXML
    private TextField taskNameField;

    @FXML
    private TextField taskDescriptionField;

    @FXML
    private ComboBox<Integer> taskPriorityComboBox;

    @FXML
    private ComboBox<String> taskStatusComboBox;

    @FXML
    private DatePicker taskDueDatePicker;

    private ProjectService projectService;
    private Project selectedProject;

    @FXML
    public void initialize() {
        IntStream.rangeClosed(1, 10).forEach(taskPriorityComboBox.getItems()::add);
        taskStatusComboBox.getItems().addAll("To Do", "In Progress", "Done");
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setProject(Project project) {
        this.selectedProject = project;
    }

    @FXML
    public void handleCancel() {
        closePopup();
    }

    @FXML
    public void handleCreateTask() {
        String taskName = taskNameField.getText();
        String taskDescription = taskDescriptionField.getText();
        Integer taskPriority = taskPriorityComboBox.getValue();
        String taskStatus = taskStatusComboBox.getValue();
        LocalDate dueDate = taskDueDatePicker.getValue();

        if (taskName == null || taskName.trim().isEmpty()) {
            showAlert("Validation Error", "Task name cannot be empty.");
            return;
        }

        if (taskPriority == null || taskStatus == null || dueDate == null) {
            showAlert("Validation Error", "All fields must be filled.");
            return;
        }

        Date dueDateConverted = java.sql.Date.valueOf(dueDate);

        int newTaskId = generateUniqueTaskId();

        Task newTask = new Task(newTaskId, taskName, taskDescription, taskPriority, taskStatus, dueDateConverted);
        selectedProject.addTask(newTask);
        projectService.addTaskToProject(selectedProject.getName(), newTask);
        projectService.updateProject(selectedProject);

        closePopup();
    }

    private void closePopup() {
        Stage stage = (Stage) taskNameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int generateUniqueTaskId() {
        return selectedProject.getTasks().keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
    }
}