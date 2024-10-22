package me.buzas.task.database;

import me.buzas.task.abstraction.data.UserDataAccess;
import me.buzas.task.model.User;

public class UserRepository implements UserDataAccess {
    @Override
    public void saveUser(User user) {

    }

    @Override
    public User loadUser(int id) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }
}
