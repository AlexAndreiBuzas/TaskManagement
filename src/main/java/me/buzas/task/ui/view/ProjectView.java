package me.buzas.task.ui.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectView extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/me/buzas/task/fxml/ProjectView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Task Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
