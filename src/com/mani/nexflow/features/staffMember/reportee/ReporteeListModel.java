package com.mani.nexflow.features.staffMember.reportee;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.List;

class ReporteeListModel {

    private final ReporteeListView reporteeListView;

    ReporteeListModel(ReporteeListView reporteeListView) {
        this.reporteeListView = reporteeListView;
    }

    List<Employee> getReportees(Long managerId) {
        return NexFlowDB.getInstance().getDirectReports(managerId);
    }
}
