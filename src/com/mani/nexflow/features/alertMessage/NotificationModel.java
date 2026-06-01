package com.mani.nexflow.features.alertMessage;

import com.mani.nexflow.data.dto.Notification;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.List;

class NotificationModel {

    private final NotificationView notificationView;

    NotificationModel(NotificationView notificationView) {
        this.notificationView = notificationView;
    }

    List<Notification> getNotifications(Long employeeId) {
        return NexFlowDB.getInstance().getNotificationsFor(employeeId);
    }

    int markAllRead(Long employeeId) {
        return NexFlowDB.getInstance().markNotificationsRead(employeeId);
    }
}
