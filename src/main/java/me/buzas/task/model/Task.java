package me.buzas.task.model;

import java.util.Date;

public class Task {
    private int id;
    private String name;
    private String description;
    private int priority;
    private String status;
    private Date dueDate;
    private int assignedUserId;

    public Task(int id, String name, String description, int priority, String status, Date dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority < 0 || priority > 10) {
            throw new IllegalArgumentException("Priority must be between 0 and 10");
        }

        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void assignUser(int userId) {
        this.assignedUserId = userId;
    }

    public int getAssignedUserId() {
        return assignedUserId;
    }
}
