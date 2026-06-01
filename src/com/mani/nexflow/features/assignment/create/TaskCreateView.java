package com.mani.nexflow.features.assignment.create;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.features.assignment.assign.TaskAssignView;
import com.mani.nexflow.util.TerminalReader;
import com.mani.nexflow.util.ParserToolkit;

import java.util.Scanner;

public class TaskCreateView {

    private final TaskCreateModel taskCreateModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public TaskCreateView(Employee currentUser) {

        this.taskCreateModel = new TaskCreateModel(this);
        this.scanner = TerminalReader.getScanner();
        this.currentUser = currentUser;

    }

    public void init() {

        System.out.println();
        System.out.println("Add a new assignment");

        String title = promptTitle();
        String description = promptDescription();
        Task.Priority priority = promptPriority();
        Long dueDate = promptDueDate();

        taskCreateModel.createTask(currentUser, title, description, priority, dueDate);
    }

    private String promptTitle() {

        while (true) {
            System.out.print("Enter assignment title: ");
            String input = scanner.nextLine();
            String error = taskCreateModel.validateTitle(input);

            if (error == null) return input.trim();
            System.out.println(error);
        }

    }


    private String promptDescription() {

        while (true) {
            System.out.print("Enter assignment description: ");
            String input = scanner.nextLine();
            String error = taskCreateModel.validateDescription(input);

            if (error == null) return input.trim();
            System.out.println(error);
        }

    }



    private Task.Priority promptPriority() {

        while (true) {
            System.out.println("Select priority:");
            System.out.println("1. P1");
            System.out.println("2. P2");
            System.out.println("3. P3");
            System.out.print("Choose an option: ");

            Task.Priority priority = taskCreateModel.parsePriority(scanner.nextLine());
            if (priority != null) return priority;

            System.out.println("Select a valid priority.");
        }

    }


    private Long promptDueDate() {

        while (true) {

            System.out.print("Enter due date (dd-MM-yyyy): ");
            Long dueDate = taskCreateModel.parseDueDate(scanner.nextLine());

            if (dueDate != null) return dueDate;
            System.out.println("Enter a valid date. Due date must be today or later.");
        }

    }


    void onTaskCreated(Task assignment) {

        System.out.println();
        System.out.println("Task created successfully.");
        System.out.println("Task id: " + assignment.getId());
        System.out.print("Do you want to assign this assignment now? (Y/N): ");

        if (ParserToolkit.isYes(scanner.nextLine())) {
            new TaskAssignView(currentUser, assignment).init();
        } else {
            System.out.println("Task saved without an assignee. Use Assign a assignment later.");
        }

    }

    void onTaskCreateFailed(String message) {
        System.out.println(message);
    }
}
