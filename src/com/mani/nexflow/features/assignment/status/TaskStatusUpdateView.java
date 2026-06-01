package com.mani.nexflow.features.assignment.status;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.util.TerminalReader;
import com.mani.nexflow.util.ParserToolkit;

import java.util.List;
import java.util.Scanner;

public class TaskStatusUpdateView {

    private final TaskStatusUpdateModel taskStatusUpdateModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public TaskStatusUpdateView(Employee currentUser) {

        this.taskStatusUpdateModel = new TaskStatusUpdateModel(this);
        this.scanner = TerminalReader.getScanner();
        this.currentUser = currentUser;

    }


    public void init() {

        System.out.println();
        System.out.println("Update assignment status");

        List<Task> tasks = taskStatusUpdateModel.getMyTasks(currentUser);

        if (tasks.isEmpty()) {
            System.out.println("You have no tasks to update.");
            return;
        }

        Task assignment = pickTask(tasks);
        if (assignment == null) return;

        System.out.println("Current status: " + nameOr(assignment.getStatus()));
        Task.TaskStatus newStatus = pickStatus();

        if (newStatus == null) return;

        String remarks = promptRemarks();
        taskStatusUpdateModel.updateStatus(assignment, newStatus, remarks, currentUser);

    }



    private Task pickTask(List<Task> tasks) {

        while (true) {
            System.out.println("Select a assignment:");

            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                System.out.println((i + 1) + ". " + t.getTitle() + " [" + nameOr(t.getStatus()) + "]");
            }

            System.out.print("Choose an option: ");
            Integer index = ParserToolkit.parseNonNegativeInt(scanner.nextLine());

            if (index != null && index >= 1 && index <= tasks.size()) {
                return tasks.get(index - 1);
            }

            System.out.println("Select a valid option.");
        }
    }



    private Task.TaskStatus pickStatus() {
        while (true) {

            System.out.println("Select new status:");
            System.out.println("1. OPEN");
            System.out.println("2. IN_PROGRESS");
            System.out.println("3. COMPLETED");
            System.out.println("4. ON_HOLD");
            System.out.println("5. CANCELLED");
            System.out.println("6. REOPENED");
            System.out.print("Choose an option: ");

            Task.TaskStatus status = taskStatusUpdateModel.parseStatus(scanner.nextLine());
            if (status != null) return status;

            System.out.println("Select a valid status.");
        }
    }


    private String promptRemarks() {
        while (true) {

            System.out.print("Enter remarks: ");
            String input = scanner.nextLine();
            String error = taskStatusUpdateModel.validateRemarks(input);
            if (error == null) return input.trim();
            System.out.println(error);

        }
    }


    void onUpdateSuccessful(Task assignment, Task.TaskStatus oldStatus) {
        System.out.println("Status updated from " + nameOr(oldStatus) + " to " + nameOr(assignment.getStatus()) + ".");
    }


    void onUpdateFailed(String message) {
        System.out.println(message);
    }


    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }

}
