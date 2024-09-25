package me.buzas.task.model;

import java.util.List;

public class User {
    private String username;
    private String team;
    private List<Task> assignedTasks;

    public User(String username, String team, List<Task> assignedTasks) {
        this.username = username;
        this.team = team;
        this.assignedTasks = assignedTasks;
    }
}
