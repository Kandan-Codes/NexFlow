package com.mani.nexflow.features.analyticsReport;

import com.mani.nexflow.util.TerminalReader;

import java.util.Scanner;

public class ReportView {

    private final ReportModel reportModel;
    private final Scanner scanner;

    public ReportView() {
        reportModel = new ReportModel(this);
        scanner = TerminalReader.getScanner();
    }

    public void init() {

        Report analyticsReport = reportModel.generateAnalyticsReport();

        System.out.println();
        System.out.println("==============================");
        System.out.println("      ANALYTICS REPORT");
        System.out.println("==============================");

        System.out.println("Total Employees      : " + analyticsReport.getTotalEmployees());
        System.out.println("Total Managers       : " + analyticsReport.getTotalManagers());
        System.out.println("Total Tasks          : " + analyticsReport.getTotalTasks());
        System.out.println("Completed Tasks      : " + analyticsReport.getCompletedTasks());
        System.out.println("Pending Tasks        : " + analyticsReport.getPendingTasks());
        System.out.println("In Progress Tasks    : " + analyticsReport.getInProgressTasks());
        System.out.println("Overdue Tasks        : " + analyticsReport.getOverdueTasks());

        System.out.println("==============================");

        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }
}
