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
        userDataAccess.saveUser(user);
    }

    @Override
    public User getUser(int id) {
        return userDataAccess.loadUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDataAccess.updateUser(user);
    }

    @Override
    public void removeUser(int id) {
        userDataAccess.deleteUser(id);
    }
}
