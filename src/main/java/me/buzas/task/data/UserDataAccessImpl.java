package me.buzas.task.data;

import me.buzas.task.abstraction.data.UserDataAccess;
import me.buzas.task.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDataAccessImpl implements UserDataAccess {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User loadUser(int id) {
        return users.get(id);
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }
}
