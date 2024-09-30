package me.buzas.task.abstraction.data;

import me.buzas.task.model.User;

public interface UserDataAccess {
    void saveUser(User user);

    User loadUser(int id);

    void updateUser(User user);

    void deleteUser(int id);
}
