package com.mani.nexflow.features.assignment.detail;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.data.dto.TaskStatusHistory;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TaskDetailModel {

    private final TaskDetailView taskDetailView;

    TaskDetailModel(TaskDetailView taskDetailView) {
        this.taskDetailView = taskDetailView;
    }


    List<Task> getVisibleTasks(Employee user) {

        List<Task> result = new ArrayList<>();
        if (user == null || user.getId() == null) return result;

        Long userId = user.getId();
        Set<Long> seen = new HashSet<>();

        for (Task assignment : NexFlowDB.getInstance().getTasksAssignedTo(userId)) {
            if (assignment.getId() != null && seen.add(assignment.getId())) result.add(assignment);
        }
        for (Task assignment : NexFlowDB.getInstance().getTasksCreatedBy(userId)) {
            if (assignment.getId() != null && seen.add(assignment.getId())) result.add(assignment);
        }

        return result;
    }


    List<TaskStatusHistory> getHistoryFor(Long taskId) {
        return NexFlowDB.getInstance().getStatusHistoryForTask(taskId);
    }



    String getEmployeeName(Long id) {

        if (id == null) return "-";

        Employee staffMember = NexFlowDB.getInstance().getEmployeeById(id);
        return (staffMember == null || staffMember.getName() == null) ? "-" : staffMember.getName();

    }


}
