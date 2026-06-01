package com.mani.nexflow.features.assignment.assign;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.util.TerminalReader;
import com.mani.nexflow.util.ParserToolkit;

import java.util.List;
import java.util.Scanner;

public class TaskAssignView {

    private final TaskAssignModel taskAssignModel;
    private final Scanner scanner;
    private final Employee currentUser;
    private final AssignMode mode;
    private final Task preselectedTask;

    public TaskAssignView(Employee currentUser, AssignMode mode) {

        this.taskAssignModel = new TaskAssignModel(this);
        this.scanner = TerminalReader.getScanner();
        this.currentUser = currentUser;
        this.mode = mode;
        this.preselectedTask = null;

    }

    public TaskAssignView(Employee currentUser, Task preselectedTask) {

        this.taskAssignModel = new TaskAssignModel(this);
        this.scanner = TerminalReader.getScanner();
        this.currentUser = currentUser;
        this.mode = AssignMode.MANAGER_ASSIGN;
        this.preselectedTask = preselectedTask;

    }

    public void init() {

        System.out.println();
        Task assignment = (preselectedTask != null) ? preselectedTask : pickTask();

        if (assignment == null) return;

        List<Employee> assignees = taskAssignModel.listAssignees(currentUser, mode);

        if (assignees.isEmpty()) {
            System.out.println("No employees available to assign.");
            return;
        }

        Employee assignee = pickAssignee(assignees);
        if (assignee == null) return;

        System.out.print("Confirm assigning assignment '" + assignment.getTitle() + "' to " + assignee.getName() + "? (Y/N): ");

        if (!ParserToolkit.isYes(scanner.nextLine())) {
            System.out.println("Assignment cancelled.");
            return;
        }

        taskAssignModel.assign(assignment, assignee.getId());
    }

    private Task pickTask() {

        List<Task> tasks = taskAssignModel.listAssignableTasks(mode, currentUser);

        if (tasks.isEmpty()) {

            if (mode == AssignMode.MANAGER_ASSIGN) {
                System.out.println("No unassigned tasks to assign.");
            } else {
                System.out.println("You have no tasks to reassign.");
            }

            return null;
        }

        String header = (mode == AssignMode.MANAGER_ASSIGN) ? "Select a assignment to assign:" : "Select a assignment to reassign:";

        while (true) {
            System.out.println(header);

            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                System.out.println((i + 1) + ". " + t.getTitle() + " [" + (t.getPriority() == null ? "-" : t.getPriority().name()) + ", " + (t.getStatus() == null ? "-" : t.getStatus().name()) + "]");
            }

            System.out.print("Choose an option: ");
            Integer index = ParserToolkit.parseNonNegativeInt(scanner.nextLine());

            if (index != null && index >= 1 && index <= tasks.size()) {
                return tasks.get(index - 1);
            }

            System.out.println("Select a valid option.");
        }
    }

    private Employee pickAssignee(List<Employee> assignees) {

        while (true) {

            System.out.println("Select an staffMember:");

            for (int i = 0; i < assignees.size(); i++) {

                Employee e = assignees.get(i);
                System.out.println((i + 1) + ". " + e.getName() + " (" + e.getEmployeeId() + ", " + (e.getRole() == null ? "-" : e.getRole().name()) + ")");
            }

            System.out.print("Choose an option: ");
            Integer index = ParserToolkit.parseNonNegativeInt(scanner.nextLine());

            if (index != null && index >= 1 && index <= assignees.size()) {
                return assignees.get(index - 1);
            }

            System.out.println("Select a valid option.");
        }
    }

    void onAssignSuccessful(Task assignment) {
        System.out.println("Task assigned successfully.");
    }

    void onAssignFailed(String message) {
        System.out.println(message);
    }
}
