package me.buzas.task.manager;

import me.buzas.task.abstraction.data.UserDataAccess;
import me.buzas.task.abstraction.manager.UserManager;
import me.buzas.task.model.User;

public class UserManagerImpl implements UserManager {

    private final UserDataAccess userDataAccess;

    public UserManagerImpl(UserDataAccess userDataAccess) {
        this.userDataAccess = userDataAccess;
    }

    @Override
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        userDataAccess.saveUser(user);
    }

    @Override
    public User getUser(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0.");
        }

        User user = userDataAccess.loadUser(id);
        if (user == null) {
            throw new IllegalArgumentException("User with ID '" + id + "' does not exist.");
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        User existingUser = userDataAccess.loadUser(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("User with ID '" + user.getId() + "' does not exist.");
        }

        userDataAccess.updateUser(user);
    }

    @Override
    public void removeUser(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0.");
        }

        User user = userDataAccess.loadUser(id);
        if (user == null) {
            throw new IllegalArgumentException("User with ID '" + id + "' does not exist.");
        }

        userDataAccess.deleteUser(id);
    }
}
