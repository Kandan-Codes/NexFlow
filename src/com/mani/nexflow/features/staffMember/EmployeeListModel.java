package com.mani.nexflow.features.staffMember;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.List;

class EmployeeListModel {

    private final EmployeeListView employeeListView;

    EmployeeListModel(EmployeeListView employeeListView) {
        this.employeeListView = employeeListView;
    }

    List<Employee> getAllEmployees() {
        return NexFlowDB.getInstance().getEmployees();
    }
}
