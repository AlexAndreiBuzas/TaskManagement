package me.buzas.task.repository;

import me.buzas.task.abstraction.data.TaskDataAccess;
import me.buzas.task.model.Task;

import java.sql.*;

public class TaskRepository implements TaskDataAccess {
    private final Connection connection;

    public TaskRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveTask(Task task) {
        String query = "INSERT INTO Task (name, description, priority, status, dueDate, assignedUserId) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setTaskData(task, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getInt(1));
                System.out.println("Task saved with ID: " + task.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Task loadTask(int id) {
        String query = "SELECT * FROM Task WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("priority"),
                        resultSet.getString("status"),
                        resultSet.getDate("dueDate")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateTask(Task task) {
        String query = "UPDATE Task SET name = ?, description = ?, priority = ?, status = ?, dueDate = ?, assignedUserId = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setTaskData(task, statement);
            statement.setInt(7, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteTask(int id) {
        String query = "DELETE FROM Task WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void setTaskData(Task task, PreparedStatement statement) throws SQLException {
        statement.setString(1, task.getName());
        statement.setString(2, task.getDescription());
        statement.setInt(3, task.getPriority());
        statement.setString(4, task.getStatus());
        statement.setDate(5, new java.sql.Date(task.getDueDate().getTime()));

        if (task.getAssignedUserId() > 0) {
            statement.setInt(6, task.getAssignedUserId());
        } else {
            statement.setNull(6, java.sql.Types.INTEGER); // Set NULL if no user is assigned
        }
    }
}
