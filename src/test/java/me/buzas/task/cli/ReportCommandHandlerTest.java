package me.buzas.task.cli;

import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportCommandHandlerTest {

    private ReportCommandHandlerImpl reportHandler;
    private ProjectManager projectManager;

    @BeforeEach
    void setUp() {
        projectManager = mock(ProjectManager.class);
        reportHandler = new ReportCommandHandlerImpl(projectManager);
        initializeTestProject();
    }

    private void initializeTestProject() {
        Project project = new Project("Test Project", "A sample project description.");
        Task task = new Task(1, "Task 1", "Task 1 Description", 5, "Open", new Date());
        project.addTask(task);

        User user = new User(1, "User1", "Team1", "Lead");
        project.addUser(user);
        task.assignUser(user.getId());

        when(projectManager.getProject("Test Project")).thenReturn(project);
    }

    @Test
    void testViewReport() {
        String userInput = "Test Project\n";
        Scanner scanner = new Scanner(userInput);
        String expectedOutput = createExpectedReportOutput();

        ByteArrayOutputStream outputStream = captureOutput(() -> reportHandler.viewReport(scanner));

        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput.trim(), actualOutput);
    }

    private String createExpectedReportOutput() {
        return """
                Enter project name:\s
                --- Project Report ---
                Project Name: Test Project
                Description: A sample project description.
                Tasks:\s
                  - Task ID: 1
                    Name: Task 1
                    Description: Task 1 Description
                    Priority: 5
                    Status: Open
                Users:\s
                  - User ID: 1
                    Username: User1
                    Team: Team1
                    Position: Lead
                    Assigned Tasks:\s
                      - Task ID: 1 (Task 1)
                """;
    }

    private ByteArrayOutputStream captureOutput(Runnable runnable) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        try {
            runnable.run();
        } finally {
            System.setOut(originalOut);
        }
        return outputStream;
    }

    @Test
    void testViewReportWithNonExistentProject() {
        String userInput = "NonExistentProject";
        Scanner scanner = new Scanner(userInput);
        String expectedOutput = "Enter project name: Error: Project with name 'NonExistentProject' does not exist.";

        ByteArrayOutputStream outputStream = captureOutput(() -> reportHandler.viewReport(scanner));

        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput.trim(), actualOutput);
    }
}