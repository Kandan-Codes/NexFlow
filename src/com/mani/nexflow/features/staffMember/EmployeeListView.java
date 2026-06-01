package com.mani.nexflow.features.staffMember;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.util.TerminalReader;

import java.util.List;
import java.util.Scanner;

public class EmployeeListView {

    private final EmployeeListModel employeeListModel;
    private final Scanner scanner;

    public EmployeeListView() {

        this.employeeListModel = new EmployeeListModel(this);
        this.scanner = TerminalReader.getScanner();

    }


    public void init() {

        List<Employee> employees = employeeListModel.getAllEmployees();

        System.out.println();
        System.out.println("All Employees");

        if (employees.isEmpty()) {

            System.out.println("No employees yet.");

        } else {

            System.out.println("#   Employee Id   Name                        Email                           Role      Status");

            for (int i = 0; i < employees.size(); i++) {

                Employee e = employees.get(i);
                String row = String.format(
                        "%-3d %-13s %-27s %-31s %-9s %s",
                        (i + 1),
                        safe(e.getEmployeeId()),
                        truncate(safe(e.getName()), 27),
                        truncate(safe(e.getEmail()), 31),
                        e.getRole() == null ? "-" : e.getRole().name(),
                        e.getStatus() == null ? "-" : e.getStatus().name());

                System.out.println(row);
            }
        }


        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }


    private String safe(String value) {
        return value == null ? "-" : value;
    }


    private String truncate(String value, int max) {

        if (value.length() <= max) return value;
        return value.substring(0, max - 1) + "~";

    }
}
