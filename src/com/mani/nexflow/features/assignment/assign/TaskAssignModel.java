package com.mani.nexflow.features.assignment.assign;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Notification;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.ArrayList;
import java.util.List;

class TaskAssignModel {

    private final TaskAssignView taskAssignView;

    TaskAssignModel(TaskAssignView taskAssignView) {
        this.taskAssignView = taskAssignView;
    }

    List<Task> listAssignableTasks(AssignMode mode, Employee currentUser) {

        if (mode == null || currentUser == null) return new ArrayList<>();

        if (mode == AssignMode.MANAGER_ASSIGN) {
            return NexFlowDB.getInstance().getUnassignedTasksCreatedBy(currentUser.getId());
        }
        return NexFlowDB.getInstance().getTasksAssignedTo(currentUser.getId());

    }

    List<Employee> listAssignees(Employee currentUser, AssignMode mode) {

        if (mode == AssignMode.MANAGER_ASSIGN) {
            return NexFlowDB.getInstance().getEmployeesExcept(null);
        }

        Long excludeId = currentUser == null ? null : currentUser.getId();
        List<Employee> all = NexFlowDB.getInstance().getEmployeesExcept(excludeId);

        if (mode == AssignMode.EMPLOYEE_REASSIGN) {

            List<Employee> filtered = new ArrayList<>();

            for (Employee candidate : all) {

                if (candidate.getRole() == Employee.Role.EMPLOYEE) {
                    filtered.add(candidate);
                }
            }

            return filtered;
        }

        return all;
    }

    void assign(Task assignment, Long assigneeId) {

        if (assignment == null || assigneeId == null) {
            taskAssignView.onAssignFailed("Could not update assignment. Please try again.");
            return;
        }

        assignment.setAssignedTo(assigneeId);
        Task updated = NexFlowDB.getInstance().updateTask(assignment);

        if (updated == null) {
            taskAssignView.onAssignFailed("Could not update assignment. Please try again.");
            return;
        }

        Notification alertMessage = new Notification();
        alertMessage.setEmployeeId(assigneeId);
        alertMessage.setTaskId(updated.getId());
        alertMessage.setType(Notification.NotificationType.TASK_ASSIGNED);
        alertMessage.setMessage("You have been assigned assignment: " + updated.getTitle());
        NexFlowDB.getInstance().addNotification(alertMessage);

        taskAssignView.onAssignSuccessful(updated);
    }
}
