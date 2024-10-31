package me.buzas.task.services;

import me.buzas.task.abstraction.manager.UserManager;
import me.buzas.task.model.User;
import me.buzas.task.repository.UserRepository;

public class UserService implements UserManager {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.saveUser(user);
    }

    @Override
    public User getUser(int id) {
        return userRepository.loadUser(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    @Override
    public void removeUser(int id) {
        userRepository.deleteUser(id);
    }
}
