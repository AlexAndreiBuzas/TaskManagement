package me.buzas.task.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Project {
    private String name;
    private String description;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, User> users;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.tasks = new HashMap<>();
        this.users = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }

    public void removeUser(int userId) {
        users.remove(userId);
    }

    public List<Task> getTasksAssignedToUser(int userId) {
        return tasks.values().stream().filter(task -> task.getAssignedUserId() == userId).collect(Collectors.toList());
    }

    public void loadTasks(List<Task> loadedTasks) {
        for (Task task : loadedTasks) {
            this.addTask(task);
        }
    }

    public void loadUsers(List<User> loadedUsers) {
        for (User user : loadedUsers) {
            this.addUser(user);
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
