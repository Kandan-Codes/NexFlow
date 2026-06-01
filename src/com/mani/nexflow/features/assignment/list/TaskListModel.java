package com.mani.nexflow.features.assignment.list;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.List;

class TaskListModel {

    private final TaskListView taskListView;

    TaskListModel(TaskListView taskListView) {
        this.taskListView = taskListView;
    }


    List<Task> getMyTasks(Long employeeId) {
        return NexFlowDB.getInstance().getTasksAssignedTo(employeeId);
    }


    String getEmployeeName(Long id) {

        if (id == null) return "-";
        Employee staffMember = NexFlowDB.getInstance().getEmployeeById(id);

        return (staffMember == null || staffMember.getName() == null) ? "-" : staffMember.getName();

    }

}
