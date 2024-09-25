package me.buzas.task.model;

public class Task {
    private String id;
    private String name;
    private String description;
    private String priority;
    private String status;
    private String dueDate;

    public Task(String id, String name, String description, String priority, String dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = "IN PROGRESS";
        this.dueDate = dueDate;
    }
}
