package me.buzas.task.manager;

import me.buzas.task.abstraction.data.ProjectDataAccess;
import me.buzas.task.abstraction.manager.ProjectManager;
import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectManagerTest {
    private ProjectManager projectManager;
    private ProjectDataAccess projectDataAccess;

    @BeforeEach
    public void setUp() {
        projectDataAccess = Mockito.mock(ProjectDataAccess.class);
        projectManager = new ProjectManagerImpl(projectDataAccess);
    }

    @Test
    public void testCreateProject() {
        String projectName = "New Project";
        String description = "This is a new project description";

        projectManager.createProject(projectName, description);

        verify(projectDataAccess, times(1)).saveProject(any(Project.class));
    }

    @Test
    public void testCreateProject_NullProjectName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectManager.createProject(null, "Valid description")
        );
        assertEquals("Project name cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testGetProject() {
        String projectName = "New Project";
        Project expectedProject = new Project(projectName, "Description");

        when(projectDataAccess.loadProject(projectName)).thenReturn(expectedProject);

        Project actualProject = projectManager.getProject(projectName);

        assertNotNull(actualProject);
        assertEquals(expectedProject.getName(), actualProject.getName());
        verify(projectDataAccess, times(1)).loadProject(projectName);
    }

    @Test
    public void testGetProject_NonExistentProject() {
        when(projectDataAccess.loadProject("Non-existent")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectManager.getProject("Non-existent")
        );
        assertEquals("Project with name 'Non-existent' does not exist.", exception.getMessage());
    }

    @Test
    public void testUpdateProject() {
        Project project = new Project("Test Project", "Initial Description");

        when(projectDataAccess.loadProject(project.getName())).thenReturn(project);

        project.setDescription("Updated Description");
        projectManager.updateProject(project);

        verify(projectDataAccess, times(1)).updateProject(project);
    }

    @Test
    public void testUpdateProject_NonExistentProject() {
        Project project = new Project("Non-existent", "Some description");

        when(projectDataAccess.loadProject(project.getName())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectManager.updateProject(project)
        );
        assertEquals("Project with name 'Non-existent' does not exist.", exception.getMessage());
    }

    @Test
    public void testDeleteProject() {
        String projectName = "Test Project";

        Project project = new Project(projectName, "Description");
        when(projectDataAccess.loadProject(projectName)).thenReturn(project);

        projectManager.deleteProject(projectName);

        verify(projectDataAccess, times(1)).deleteProject(projectName);
    }

    @Test
    public void testDeleteProject_NonExistentProject() {
        when(projectDataAccess.loadProject("Non-existent")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectManager.deleteProject("Non-existent")
        );
        assertEquals("Project with name 'Non-existent' does not exist.", exception.getMessage());
    }

    @Test
    public void testAddUserToProject() {
        String projectName = "Test Project";
        User expectedUser = new User(1, "john_doe", "Engineering", "Developer");
        Project project = new Project(projectName, "Description");

        when(projectDataAccess.loadProject(projectName)).thenReturn(project);

        projectManager.addUserToProject(projectName, expectedUser);

        User actualUser = project.getUsers().get(expectedUser.getId());

        assertEquals(expectedUser, actualUser);
        verify(projectDataAccess, times(1)).updateProject(project);
    }

    @Test
    public void testAddUserToProject_NonExistentProject() {
        String projectName = "Non-existent";
        when(projectDataAccess.loadProject(projectName)).thenReturn(null);

        User user = new User(1, "john_doe", "Engineering", "Developer");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectManager.addUserToProject(projectName, user)
        );
        assertEquals("Project with name '" + projectName + "' does not exist.", exception.getMessage());
    }

    @Test
    public void testRemoveUserFromProject() {
        String projectName = "Test Project";
        User user = new User(1, "john_doe", "Engineering", "Developer");
        Project project = new Project(projectName, "Description");
        project.addUser(user);

        when(projectDataAccess.loadProject(projectName)).thenReturn(project);

        projectManager.removeUserFromProject(projectName, user.getId());

        Assertions.assertNull(project.getUsers().get(user.getId()));
        verify(projectDataAccess, times(1)).updateProject(project);
    }

    @Test
    public void testRemoveUserFromProject_NonExistentUser() {
        String projectName = "Test Project";
        int userId = 99;
        Project project = new Project(projectName, "Description");

        when(projectDataAccess.loadProject(projectName)).thenReturn(project);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectManager.removeUserFromProject(projectName, userId)
        );
        assertEquals("User with ID '" + userId + "' does not exist in project '" + projectName + "'.", exception.getMessage());
    }

    @Test
    public void testAddTaskToProject() {
        String projectName = "Test Project";
        Task expectedTask = new Task(1, "Task 1", "Description 1", 1, "Open", new java.util.Date());
        Project project = new Project(projectName, "Description");

        when(projectDataAccess.loadProject(projectName)).thenReturn(project);

        projectManager.addTaskToProject(projectName, expectedTask);

        Task actualTask = project.getTasks().get(expectedTask.getId());

        assertEquals(expectedTask, actualTask);
        verify(projectDataAccess, times(1)).updateProject(project);
    }

    @Test
    public void testAddTaskToProject_NonExistentProject() {
        when(projectDataAccess.loadProject("Non-existent")).thenReturn(null);

        Task task = new Task(1, "Task 1", "Description 1", 1, "Open", new java.util.Date());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectManager.addTaskToProject("Non-existent", task)
        );
        assertEquals("Project with name 'Non-existent' does not exist.", exception.getMessage());
    }

    @Test
    public void testRemoveTaskFromProject() {
        String projectName = "Test Project";
        Task task = new Task(1, "Task 1", "Description 1", 1, "Open", new java.util.Date());
        Project project = new Project(projectName, "Description");
        project.addTask(task);

        when(projectDataAccess.loadProject(projectName)).thenReturn(project);

        projectManager.removeTaskFromProject(projectName, task.getId());

        Assertions.assertNull(project.getTasks().get(task.getId()));
        verify(projectDataAccess, times(1)).updateProject(project);
    }

    @Test
    public void testRemoveTaskFromProject_NonExistentTask() {
        String projectName = "Test Project";
        int taskId = 99;
        Project project = new Project(projectName, "Description");

        when(projectDataAccess.loadProject(projectName)).thenReturn(project);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectManager.removeTaskFromProject(projectName, taskId)
        );
        assertEquals("Task with ID '" + taskId + "' does not exist in project '" + projectName + "'.", exception.getMessage());
    }

    @Test
    public void testAssignTaskToUser() {
        String projectName = "Test Project";
        User user = new User(1, "john_doe", "Engineering", "Developer");
        Task task = new Task(1, "Task 1", "Description 1", 1, "Open", new java.util.Date());
        Project project = new Project(projectName, "Description");
        project.addUser(user);
        project.addTask(task);

        when(projectDataAccess.loadProject(projectName)).thenReturn(project);

        projectManager.assignTaskToUser(projectName, task.getId(), user.getId());

        assertEquals(user.getId(), task.getAssignedUserId());
        verify(projectDataAccess, times(1)).updateProject(project);
    }

    @Test
    public void testAssignTaskToUser_NonExistentTask() {
        String projectName = "Test Project";
        int taskId = 99;
        User user = new User(1, "john_doe", "Engineering", "Developer");
        Project project = new Project(projectName, "Description");

        when(projectDataAccess.loadProject(projectName)).thenReturn(project);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectManager.assignTaskToUser(projectName, taskId, user.getId())
        );
        assertEquals("Task with ID '" + taskId + "' does not exist in project '" + projectName + "'.", exception.getMessage());
    }
}