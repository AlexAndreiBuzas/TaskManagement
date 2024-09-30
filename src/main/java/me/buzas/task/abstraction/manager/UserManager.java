package me.buzas.task.abstraction.manager;

import me.buzas.task.model.User;

public interface UserManager {
    void addUser(User user);

    User getUser(int id);

    void updateUser(User user);

    void removeUser(int id);
}
