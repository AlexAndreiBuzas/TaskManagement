package me.buzas.task.abstraction.cli;

import java.util.Scanner;

public interface UserCommandHandler {
    void addUser(Scanner scanner);

    void removeUser(Scanner scanner);
}
