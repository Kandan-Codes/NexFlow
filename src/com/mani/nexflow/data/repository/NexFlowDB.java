package com.mani.nexflow.data.repository;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.Notification;
import com.mani.nexflow.data.dto.Task;
import com.mani.nexflow.data.dto.TaskStatusHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NexFlowDB {

    private NexFlowDB() {
    }

    private static NexFlowDB nexFlowDB = null;

    public static NexFlowDB getInstance() {

        if (nexFlowDB == null) {
            nexFlowDB = new NexFlowDB();
        }

        return nexFlowDB;
    }

    //=============================== STORAGE ==================================

    private final List<Employee> employees = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final List<TaskStatusHistory> taskStatusHistories = new ArrayList<>();
    private final List<Notification> notifications = new ArrayList<>();

    private long employeePk = 0L;
    private long taskPk = 0L;
    private long taskStatusHistoryPk = 0L;
    private long notificationPk = 0L;

    public Employee addEmployee(Employee staffMember) {

        if (staffMember == null) return null;
        if (staffMember.getEmail() == null || staffMember.getEmail().trim().isEmpty()) return null;

        employeePk++;
        staffMember.setId(employeePk);

        staffMember.setEmployeeId(String.format(Locale.ROOT, "EMP%05d", employeePk));

        if (staffMember.getCreatedAt() == null) {
            staffMember.setCreatedAt(System.currentTimeMillis());
        }
        if (staffMember.getStatus() == null) {
            staffMember.setStatus(Employee.EmployeeStatus.ACTIVE);
        }
        if (staffMember.getRole() == null) {
            staffMember.setRole(Employee.Role.EMPLOYEE);
        }

        employees.add(staffMember);

        return staffMember;
    }


    public Employee getEmployeeByEmail(String email) {

        if (email == null) return null;
        String key = email.trim().toLowerCase(Locale.ROOT);

        if (key.isEmpty()) return null;

        for (Employee current : employees) {

            if (current.getEmail() != null && current.getEmail().trim().toLowerCase(Locale.ROOT).equals(key)) {
                return current;
            }
        }

        return null;
    }



    public Employee authenticateEmployee(String email, String accessKey) {

        Employee staffMember = getEmployeeByEmail(email);
        if (staffMember == null) return null;

        if (accessKey == null || !accessKey.equals(staffMember.getPassword())) return null;

        return staffMember;
    }



    public Employee getEmployeeById(Long id) {

        if (id == null) return null;

        for (Employee current : employees) {
            if (id.equals(current.getId())) return current;
        }

        return null;
    }


    public List<Employee> getEmployees() {
    return new ArrayList<>(employees);
    }


    public List<Employee> getEmployeesExcept(Long excludeId) {

        List<Employee> result = new ArrayList<>();

        for (Employee current : employees) {

            if (current.getStatus() != Employee.EmployeeStatus.ACTIVE) continue;
            if (excludeId != null && excludeId.equals(current.getId())) continue;
            result.add(current);
        }

        return result;
    }



    public List<Employee> getActiveManagers() {

        List<Employee> result = new ArrayList<>();

        for (Employee current : employees) {

            if (current.getRole() == Employee.Role.MANAGER && current.getStatus() == Employee.EmployeeStatus.ACTIVE) {
                result.add(current);
            }
        }

        return result;
    }



    public Task addTask(Task assignment) {

        if (assignment == null) return null;

        taskPk++;
        assignment.setId(taskPk);

        long now = System.currentTimeMillis();
        if (assignment.getCreatedTime() == null) assignment.setCreatedTime(now);
        assignment.setUpdatedTime(now);

        if (assignment.getStatus() == null) assignment.setStatus(Task.TaskStatus.OPEN);
        tasks.add(assignment);

        return assignment;
    }


    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }


    public Task updateTask(Task assignment) {

        if (assignment == null || assignment.getId() == null) return null;

        for (int i = 0; i < tasks.size(); i++) {

            if (assignment.getId().equals(tasks.get(i).getId())) {
                assignment.setUpdatedTime(System.currentTimeMillis());
                tasks.set(i, assignment);
                return assignment;
            }
        }

        return null;
    }


    public List<Task> getTasksAssignedTo(Long employeeId) {

        List<Task> result = new ArrayList<>();
        if (employeeId == null) return result;

        for (Task current : tasks) {
            if (employeeId.equals(current.getAssignedTo())) result.add(current);
        }

        return result;
    }



    public List<Task> getTasksCreatedBy(Long assignedById) {

        List<Task> result = new ArrayList<>();
        if (assignedById == null) return result;

        for (Task current : tasks) {
            if (assignedById.equals(current.getAssignedBy())) result.add(current);
        }

        return result;
    }



    public List<Task> getUnassignedTasksCreatedBy(Long managerId) {

        List<Task> result = new ArrayList<>();
        if (managerId == null) return result;

        for (Task current : tasks) {

            if (current.getAssignedTo() == null && managerId.equals(current.getAssignedBy())) {
                result.add(current);
            }
        }

        return result;
    }


    public List<Employee> getDirectReports(Long managerId) {

        List<Employee> result = new ArrayList<>();
        if (managerId == null) return result;

        for (Employee current : employees) {
            if (managerId.equals(current.getReportingTo())) result.add(current);
        }

        return result;
    }


    public TaskStatusHistory addStatusHistory(TaskStatusHistory history) {

        if (history == null || history.getTaskId() == null) return null;

        taskStatusHistoryPk++;
        history.setId(taskStatusHistoryPk);

        if (history.getChangedTime() == null) {
            history.setChangedTime(System.currentTimeMillis());
        }

        taskStatusHistories.add(history);

        return history;
    }

    public List<TaskStatusHistory> getStatusHistoryForTask(Long taskId) {
        List<TaskStatusHistory> result = new ArrayList<>();
        if (taskId == null) return result;

        for (TaskStatusHistory current : taskStatusHistories) {
            if (taskId.equals(current.getTaskId())) result.add(current);
        }
        return result;
    }

    public Notification addNotification(Notification alertMessage) {
        if (alertMessage == null || alertMessage.getEmployeeId() == null) return null;

        notificationPk++;
        alertMessage.setId(notificationPk);

        if (alertMessage.getCreatedTime() == null) {
            alertMessage.setCreatedTime(System.currentTimeMillis());
        }
        if (alertMessage.getIsRead() == null) {
            alertMessage.setIsRead(Boolean.FALSE);
        }

        notifications.add(alertMessage);

        return alertMessage;
    }

    public List<Notification> getNotificationsFor(Long employeeId) {
        List<Notification> result = new ArrayList<>();

        if (employeeId == null) return result;

        for (Notification current : notifications) {
            if (employeeId.equals(current.getEmployeeId())) result.add(current);
        }

        return result;
    }

    public int markNotificationsRead(Long employeeId) {
        if (employeeId == null) return 0;
        int count = 0;

        for (Notification current : notifications) {

            if (employeeId.equals(current.getEmployeeId()) && !Boolean.TRUE.equals(current.getIsRead())) {
                current.setIsRead(Boolean.TRUE);
                count++;
            }
        }
        return count;
    }
}
