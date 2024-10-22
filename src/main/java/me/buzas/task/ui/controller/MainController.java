package me.buzas.task.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    @FXML
    private Button exampleButton;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        System.out.println("Button clicked!");
    }
}
