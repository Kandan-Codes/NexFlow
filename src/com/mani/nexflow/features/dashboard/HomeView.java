package com.mani.nexflow.features.dashboard;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.features.staffMember.EmployeeListView;
import com.mani.nexflow.features.staffMember.add.EmployeeAddView;
import com.mani.nexflow.features.staffMember.detail.EmployeeDetailView;
import com.mani.nexflow.features.staffMember.reportee.ReporteeListView;
import com.mani.nexflow.features.alertMessage.NotificationView;
import com.mani.nexflow.features.analyticsReport.ReportView;
import com.mani.nexflow.features.assignment.assign.AssignMode;
import com.mani.nexflow.features.assignment.assign.TaskAssignView;
import com.mani.nexflow.features.assignment.create.TaskCreateView;
import com.mani.nexflow.features.assignment.detail.TaskDetailView;
import com.mani.nexflow.features.assignment.list.TaskListView;
import com.mani.nexflow.features.assignment.status.TaskStatusUpdateView;
import com.mani.nexflow.features.assignment.team.TeamStatusView;
import com.mani.nexflow.util.TerminalReader;

import java.util.Scanner;

public class HomeView {

    private final HomeModel homeModel;
    private final Employee staffMember;
    private final Scanner scanner;

    public HomeView(Employee staffMember) {
        this.homeModel = new HomeModel(this);
        this.staffMember = staffMember;
        this.scanner = TerminalReader.getScanner();
    }

    public void init() {
        homeModel.init(staffMember);
    }

    void showUnauthorized() {
        System.out.println("Your account role is not set. Contact your administrator.");
    }

    void showManagerMenu() {

        while (true) {

            System.out.println();
            System.out.println("============== Manager Home =================");
            System.out.println("1.  My profile");
            System.out.println("2.  Screen all employees");
            System.out.println("3.  Screen reportees");
            System.out.println("4.  Add staffMember");
            System.out.println("5.  Add new assignment");
            System.out.println("6.  Assign a assignment");
            System.out.println("7.  Screen team status");
            System.out.println("8.  Update my assignment status");
            System.out.println("9.  Screen assignment details");
            System.out.println("10.  Notifications");
            System.out.println("11. Screen reports");
            System.out.println("12. Sign out");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    new EmployeeDetailView(staffMember).init();
                    break;

                case "2":
                    new EmployeeListView().init();
                    break;

                case "3":
                    new ReporteeListView(staffMember).init();
                    break;

                case "4":
                    new EmployeeAddView(staffMember).init();
                    break;

                case "5":
                    new TaskCreateView(staffMember).init();
                    break;

                case "6":
                    new TaskAssignView(staffMember, AssignMode.MANAGER_ASSIGN).init();
                    break;

                case "7":
                    new TeamStatusView(staffMember).init();
                    break;

                case "8":
                    new TaskStatusUpdateView(staffMember).init();
                    break;

                case "9":
                    new TaskDetailView(staffMember).init();
                    break;

                case "10":
                    new NotificationView(staffMember).init();
                    break;

                case "11":
                    new ReportView().init();
                    break;

                case "12":
                    System.out.println("You have been signed out.");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    void showEmployeeMenu() {

        while (true) {

            System.out.println();
            System.out.println("====================== Employee Home ====================");
            System.out.println("1. My Profile");
            System.out.println("2. My tasks");
            System.out.println("3. Update assignment status");
            System.out.println("4. Reassign a assignment");
            System.out.println("5. Screen assignment details");
            System.out.println("6. Notifications");
            System.out.println("7. Sign out");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    new EmployeeDetailView(staffMember).init();
                    break;

                case "2":
                    new TaskListView(staffMember).init();
                    break;

                case "3":
                    new TaskStatusUpdateView(staffMember).init();
                    break;

                case "4":
                    new TaskAssignView(staffMember, AssignMode.EMPLOYEE_REASSIGN).init();
                    break;

                case "5":
                    new TaskDetailView(staffMember).init();
                    break;

                case "6":
                    new NotificationView(staffMember).init();
                    break;

                case "7":
                    System.out.println("You have been signed out.");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
