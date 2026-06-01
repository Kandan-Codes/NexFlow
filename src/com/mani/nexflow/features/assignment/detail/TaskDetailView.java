package com.mani.nexflow.features.assignment.detail;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.data.dto.TaskStatusHistory;
import com.mani.nexflow.util.TerminalReader;
import com.mani.nexflow.util.ParserToolkit;

import java.util.List;
import java.util.Scanner;

public class TaskDetailView {

    private final TaskDetailModel taskDetailModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public TaskDetailView(Employee currentUser) {

        this.taskDetailModel = new TaskDetailModel(this);
        this.scanner = TerminalReader.getScanner();
        this.currentUser = currentUser;

    }


    public void init() {

        System.out.println();
        System.out.println("Task details");
        List<Task> tasks = taskDetailModel.getVisibleTasks(currentUser);

        if (tasks.isEmpty()) {
            System.out.println("You have no tasks to view.");
            return;
        }

        Task assignment = pickTask(tasks);
        if (assignment == null) return;

        printTask(assignment);
        printHistory(assignment);

        System.out.print("Press Enter to return: ");
        scanner.nextLine();

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



    private void printTask(Task assignment) {

        System.out.println();
        System.out.println("Id           : " + assignment.getId());
        System.out.println("Title        : " + assignment.getTitle());
        System.out.println("Description  : " + assignment.getDescription());
        System.out.println("Priority     : " + nameOr(assignment.getPriority()));
        System.out.println("Status       : " + nameOr(assignment.getStatus()));
        System.out.println("Assigned by  : " + taskDetailModel.getEmployeeName(assignment.getAssignedBy()));
        System.out.println("Assigned to  : " + taskDetailModel.getEmployeeName(assignment.getAssignedTo()));
        System.out.println("Due date     : " + ParserToolkit.formatDate(assignment.getDueDate()));
        System.out.println("Created at   : " + ParserToolkit.formatDateTime(assignment.getCreatedTime()));
        System.out.println("Updated at   : " + ParserToolkit.formatDateTime(assignment.getUpdatedTime()));
        System.out.println("Completed at : " + ParserToolkit.formatDateTime(assignment.getCompletedTime()));

    }



    private void printHistory(Task assignment) {

        System.out.println();
        System.out.println("Status history");

        List<TaskStatusHistory> history = taskDetailModel.getHistoryFor(assignment.getId());

        if (history.isEmpty()) {
            System.out.println("No status history yet.");
            return;
        }

        for (TaskStatusHistory entry : history) {
            System.out.println(ParserToolkit.formatDateTime(entry.getChangedTime()) + " | " + taskDetailModel.getEmployeeName(entry.getChangedBy()) + " | " + nameOr(entry.getOldStatus()) + " -> " + nameOr(entry.getNewStatus()) + " | " + (entry.getRemarks() == null ? "" : entry.getRemarks()));
        }

    }



    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }

}
