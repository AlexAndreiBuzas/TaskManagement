package me.buzas.task.abstraction.cli;

import java.util.Scanner;

public interface TaskCommandHandler {
    void addTask(Scanner scanner);

    void assignTaskToUser(Scanner scanner);

    void removeTask(Scanner scanner);
}
