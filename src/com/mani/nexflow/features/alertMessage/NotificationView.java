package com.mani.nexflow.features.alertMessage;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Notification;
import com.mani.nexflow.util.TerminalReader;
import com.mani.nexflow.util.ParserToolkit;

import java.util.List;
import java.util.Scanner;

public class NotificationView {

    private final NotificationModel notificationModel;
    private final Scanner scanner;
    private final Employee currentUser;

    public NotificationView(Employee currentUser) {

        this.notificationModel = new NotificationModel(this);
        this.scanner = TerminalReader.getScanner();
        this.currentUser = currentUser;

    }

    public void init() {

        System.out.println();
        System.out.println("Notifications");

        Long userId = (currentUser == null) ? null : currentUser.getId();
        List<Notification> notifications = notificationModel.getNotifications(userId);

        if (notifications.isEmpty()) {

            System.out.println("You have no notifications.");
            System.out.print("Press Enter to return: ");
            scanner.nextLine();

            return;
        }

        int unreadCount = 0;
        for (int i = 0; i < notifications.size(); i++) {

            Notification n = notifications.get(i);
            boolean unread = !Boolean.TRUE.equals(n.getIsRead());

            if (unread) unreadCount++;
            String marker = unread ? "*" : " ";

            System.out.println((i + 1) + ". [" + marker + "] " + ParserToolkit.formatDateTime(n.getCreatedTime()) + " | " + nameOr(n.getType()) + " | " + safe(n.getMessage()));
        }

        if (unreadCount > 0) {
            System.out.print("Mark all as read? (Y/N): ");

            if (ParserToolkit.isYes(scanner.nextLine())) {
                int marked = notificationModel.markAllRead(userId);
                System.out.println(marked + " alertMessage(s) marked as read.");
            }

        } else {

            System.out.print("Press Enter to return: ");
            scanner.nextLine();
        }
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String nameOr(Enum<?> value) {
        return value == null ? "-" : value.name();
    }
}
