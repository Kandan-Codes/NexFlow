package com.mani.nexflow.features.assignment.team;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.List;

class TeamStatusModel {

    private final TeamStatusView teamStatusView;

    TeamStatusModel(TeamStatusView teamStatusView) {
        this.teamStatusView = teamStatusView;
    }

    List<Employee> getDirectReports(Long managerId) {
        return NexFlowDB.getInstance().getDirectReports(managerId);
    }

    List<Task> getTasksFor(Long employeeId) {
        return NexFlowDB.getInstance().getTasksAssignedTo(employeeId);
    }
}
