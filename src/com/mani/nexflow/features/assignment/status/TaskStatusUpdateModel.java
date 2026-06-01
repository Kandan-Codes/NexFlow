package com.mani.nexflow.features.assignment.status;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Notification;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.data.dto.TaskStatusHistory;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.List;

class TaskStatusUpdateModel {

    private static final int MAX_REMARKS = 500;

    private final TaskStatusUpdateView taskStatusUpdateView;

    TaskStatusUpdateModel(TaskStatusUpdateView taskStatusUpdateView) {
        this.taskStatusUpdateView = taskStatusUpdateView;
    }


    List<Task> getMyTasks(Employee currentUser) {

        Long id = (currentUser == null) ? null : currentUser.getId();
        return NexFlowDB.getInstance().getTasksAssignedTo(id);

    }



    Task.TaskStatus parseStatus(String choice) {

        if (choice == null) return null;
        String c = choice.trim();

        if (c.equals("1") || c.equalsIgnoreCase("OPEN")) return Task.TaskStatus.OPEN;
        if (c.equals("2") || c.equalsIgnoreCase("IN_PROGRESS")) return Task.TaskStatus.IN_PROGRESS;
        if (c.equals("3") || c.equalsIgnoreCase("COMPLETED")) return Task.TaskStatus.COMPLETED;
        if (c.equals("4") || c.equalsIgnoreCase("ON_HOLD")) return Task.TaskStatus.ON_HOLD;
        if (c.equals("5") || c.equalsIgnoreCase("CANCELLED")) return Task.TaskStatus.CANCELLED;
        if (c.equals("6") || c.equalsIgnoreCase("REOPENED")) return Task.TaskStatus.REOPENED;

        return null;
    }



    String validateRemarks(String remarks) {

        if (remarks == null || remarks.trim().isEmpty()) return "Remarks cannot be empty";

        if (remarks.trim().length() > MAX_REMARKS) {
            return "Remarks cannot exceed " + MAX_REMARKS + " characters";
        }

        return null;
    }



    void updateStatus(Task assignment, Task.TaskStatus newStatus, String remarks, Employee changedBy) {

        if (assignment == null || newStatus == null || changedBy == null) {
            taskStatusUpdateView.onUpdateFailed("Could not update status. Please try again.");
            return;
        }

        if (newStatus == assignment.getStatus()) {
            taskStatusUpdateView.onUpdateFailed("New status must differ from current status.");
            return;
        }


        Task.TaskStatus oldStatus = assignment.getStatus();

        TaskStatusHistory history = new TaskStatusHistory();
        history.setTaskId(assignment.getId());
        history.setChangedBy(changedBy.getId());
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setRemarks(remarks == null ? "" : remarks.trim());
        NexFlowDB.getInstance().addStatusHistory(history);

        assignment.setStatus(newStatus);

        if (newStatus == Task.TaskStatus.COMPLETED) {
            assignment.setCompletedTime(System.currentTimeMillis());

        } else if (newStatus == Task.TaskStatus.REOPENED) {
            assignment.setCompletedTime(null);
        }


        Task updated = NexFlowDB.getInstance().updateTask(assignment);

        if (updated == null) {
            taskStatusUpdateView.onUpdateFailed("Could not update status. Please try again.");
            return;
        }

        notifyCreator(updated, oldStatus, newStatus, changedBy);
        taskStatusUpdateView.onUpdateSuccessful(updated, oldStatus);
    }



    private void notifyCreator(Task assignment, Task.TaskStatus oldStatus, Task.TaskStatus newStatus, Employee changedBy) {

        Long creatorId = assignment.getAssignedBy();

        if (creatorId == null) return;
        if (changedBy != null && creatorId.equals(changedBy.getId())) return;

        Notification alertMessage = new Notification();

        alertMessage.setEmployeeId(creatorId);
        alertMessage.setTaskId(assignment.getId());
        alertMessage.setType(Notification.NotificationType.STATUS_UPDATED);

        String who = (changedBy == null || changedBy.getName() == null) ? "an staffMember" : changedBy.getName();
        alertMessage.setMessage("Task '" + assignment.getTitle() + "' status changed from " + oldStatus + " to " + newStatus + " by " + who);

        NexFlowDB.getInstance().addNotification(alertMessage);
    }


}
