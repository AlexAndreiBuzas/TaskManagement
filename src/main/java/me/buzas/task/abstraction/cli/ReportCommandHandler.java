package me.buzas.task.abstraction.cli;

import me.buzas.task.model.Project;

public interface ReportCommandHandler {
    void viewReport(Project project);

    void exportReport(Project project, String fileName);
}
