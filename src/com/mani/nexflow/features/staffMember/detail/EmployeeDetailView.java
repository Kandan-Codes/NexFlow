package com.mani.nexflow.features.staffMember.detail;

import com.mani.nexflow.data.dto.Employee;

public class EmployeeDetailView {
    private final Employee employee;

    public EmployeeDetailView(Employee employee) {
        this.employee = employee;
    }

    public void init() {
        System.out.println("======================== My Profile ========================== ");

        System.out.println("Employee ID:     " + employee.getEmployeeId());
        System.out.println("Employee Name:   " + employee.getName());
        System.out.println("Employee Email:  " + employee.getEmail());
        System.out.println("Employee Mobile: " + employee.getMobileNo());
        System.out.println("Employee DOB:    " + employee.getDob());
        System.out.println("Employee Role:   " + employee.getRole());

        System.out.println("==============================================================");
    }
}
