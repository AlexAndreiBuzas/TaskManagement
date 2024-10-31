package me.buzas.task.repository;

import me.buzas.task.abstraction.data.UserDataAccess;
import me.buzas.task.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements UserDataAccess {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveUser(User user) {
        String query = "INSERT INTO User (username, password, team, positionInsideTeam) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getTeam());
            statement.setString(4, user.getPositionInsideTeam());
            statement.executeUpdate();

            System.out.println("User saved: " + user.getUsername());

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public User loadUser(int id) {
        String query = "SELECT * FROM User WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("team"), resultSet.getString("positionInsideTeam"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        String query = "UPDATE User SET username = ?, password = ?, team = ?, positionInsideTeam = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getTeam());
            statement.setString(4, user.getPositionInsideTeam());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(int id) {
        String query = "DELETE FROM User WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
