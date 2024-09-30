package me.buzas.task.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final int id;
    private String username;
    private String team;
    private String positionInsideTeam;
    private final Map<Integer, Task> task;

    public User(int id, String username, String team, String positionInsideTeam) {
        this.id = id;
        this.username = username;
        this.team = team;
        this.positionInsideTeam = positionInsideTeam;
        this.task = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPositionInsideTeam() {
        return positionInsideTeam;
    }

    public void setPositionInsideTeam(String positionInsideTeam) {
        this.positionInsideTeam = positionInsideTeam;
    }

    public Map<Integer, Task> getTask() {
        return task;
    }

    public void addTask(Task task) {
        this.task.put(task.getId(), task);
    }
}
