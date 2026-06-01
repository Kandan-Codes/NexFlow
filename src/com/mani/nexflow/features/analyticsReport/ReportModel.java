package com.mani.nexflow.features.analyticsReport;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.List;

class ReportModel {

    private final ReportView reportView;

    ReportModel(ReportView reportView) {
        this.reportView = reportView;
    }

    com.mani.nexflow.features.analyticsReport.Report generateAnalyticsReport() {

        Report analyticsReport = new Report();

        List<Employee> employees = NexFlowDB.getInstance().getEmployees();
        List<Task> tasks = NexFlowDB.getInstance().getAllTasks();

        analyticsReport.setTotalEmployees(employees.size());

        int managerCount = 0;

        for (Employee staffMember : employees) {
            if (staffMember.getRole() == Employee.Role.MANAGER) {
                managerCount++;
            }
        }

        analyticsReport.setTotalManagers(managerCount);
        analyticsReport.setTotalTasks(tasks.size());

        int completed = 0;
        int pending = 0;
        int inProgress = 0;
        int overdue = 0;

        long currentTime = System.currentTimeMillis();

        for (Task assignment : tasks) {

            if (assignment.getStatus() == Task.TaskStatus.COMPLETED) {
                completed++;
            } else if (assignment.getStatus() == Task.TaskStatus.IN_PROGRESS) {
                inProgress++;
            } else {
                pending++;
            }

            if (assignment.getDueDate() != null && assignment.getDueDate() < currentTime && assignment.getStatus() != Task.TaskStatus.COMPLETED) {
                overdue++;
            }
        }

        analyticsReport.setCompletedTasks(completed);
        analyticsReport.setPendingTasks(pending);
        analyticsReport.setInProgressTasks(inProgress);
        analyticsReport.setOverdueTasks(overdue);

        return analyticsReport;
    }
}
