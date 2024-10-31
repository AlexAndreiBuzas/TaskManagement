package me.buzas.task.repository;

import me.buzas.task.abstraction.data.ProjectDataAccess;
import me.buzas.task.model.Project;
import me.buzas.task.model.Task;
import me.buzas.task.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository implements ProjectDataAccess {
    private final Connection connection;

    public ProjectRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveProject(Project project) {
        String query = "INSERT INTO Project (name, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Project loadProject(String projectName) {
        String query = "SELECT * FROM Project WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, projectName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project(resultSet.getString("name"), resultSet.getString("description"));

                List<Task> tasks = loadTasksForProject(projectName);
                project.loadTasks(tasks);

                return project;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateProject(Project project) {
        String query = "UPDATE Project SET description = ? WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getDescription());
            statement.setString(2, project.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteProject(String projectName) {
        String query = "DELETE FROM Project WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, projectName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Project> loadAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM Project";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                projects.add(new Project(resultSet.getString("name"), resultSet.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return projects;
    }

    private List<Task> loadTasksForProject(String projectName) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM Task WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, projectName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("priority"),
                        resultSet.getString("status"),
                        resultSet.getDate("dueDate")
                );
                task.assignUser(resultSet.getInt("assignedUserId"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    private List<User> loadUsersForProject(String projectName) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, projectName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("team"),
                        resultSet.getString("positionInsideTeam")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
