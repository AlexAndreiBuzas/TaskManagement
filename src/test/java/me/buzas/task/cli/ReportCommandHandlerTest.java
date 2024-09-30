package me.buzas.task.cli;

import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.model.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportCommandHandlerTest {
    @Test
    void testViewReport() {
        Project project = new Project("Test Project", "A sample project description.");

        Task task = new Task(1, "Task 1", "Task 1 Description", 5, "Open", new Date());
        project.addTask(task);

        User user = new User(1, "User1", "Team1", "Lead");
        project.addUser(user);

        task.assignUser(user.getId());

        ReportCommandHandlerImpl reportHandler = new ReportCommandHandlerImpl();

        String expectedOutput = "\n--- Project Report ---\n" +
                "Project Name: Test Project\n" +
                "Description: A sample project description.\n" +
                "Tasks: \n" +
                "  - Task ID: 1\n" +
                "    Name: Task 1\n" +
                "    Description: Task 1 Description\n" +
                "    Priority: 5\n" +
                "    Status: Open\n" +
                "Users: \n" +
                "  - User ID: 1\n" +
                "    Username: User1\n" +
                "    Team: Team1\n" +
                "    Position: Lead\n" +
                "    Assigned Tasks: \n" +
                "      - Task ID: 1 (Task 1)\n";

        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));

        reportHandler.viewReport(project);

        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput.trim(), actualOutput.trim());
    }
}
