package com.mani.nexflow.features.assignment.team;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.util.TerminalReader;
import com.mani.nexflow.util.ParserToolkit;

import java.util.List;
import java.util.Scanner;

public class TeamStatusView {

    private final TeamStatusModel teamStatusModel;
    private final Scanner scanner;
    private final Employee manager;

    public TeamStatusView(Employee manager) {

        this.teamStatusModel = new TeamStatusModel(this);
        this.scanner = TerminalReader.getScanner();
        this.manager = manager;

    }


    public void init() {
        System.out.println();
        System.out.println("Team Status");

        Long managerId = (manager == null) ? null : manager.getId();
        List<Employee> reports = teamStatusModel.getDirectReports(managerId);

        if (reports.isEmpty()) {

            System.out.println("You have no reporting employees.");

        } else {

            for (Employee analyticsReport : reports) {

                System.out.println();
                System.out.println(analyticsReport.getName() + " (" + analyticsReport.getEmployeeId() + ")");
                List<Task> tasks = teamStatusModel.getTasksFor(analyticsReport.getId());

                if (tasks.isEmpty()) {
                    System.out.println("  No tasks assigned");
                } else {

                    for (Task assignment : tasks) {
                        System.out.println("  - [" + nameOr(assignment.getPriority()) + "] " + assignment.getTitle() + " [" + nameOr(assignment.getStatus()) + "] due " + ParserToolkit.formatDate(assignment.getDueDate()));
                    }

                }
            }
        }


        System.out.print("Press Enter to return: ");
        scanner.nextLine();

    }


    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }

}
