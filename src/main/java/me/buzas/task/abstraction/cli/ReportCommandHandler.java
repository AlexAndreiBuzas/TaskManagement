package me.buzas.task.abstraction.cli;

import java.util.Scanner;

public interface ReportCommandHandler {
    void viewReport(Scanner scanner);

    void exportReport(Scanner scanner);
}
