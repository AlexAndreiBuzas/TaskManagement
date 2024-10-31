module TaskManagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;

    exports me.buzas.task.ui.view to javafx.graphics;

    opens me.buzas.task.ui.controller to javafx.fxml;
}