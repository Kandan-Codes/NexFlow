package com.mani.nexflow.features.staffMember.add;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.repository.NexFlowDB;
import com.mani.nexflow.util.ParserToolkit;

import java.util.regex.Pattern;

class EmployeeAddModel {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d).{8,}$");

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_AGE_YEARS = 18;

    private final EmployeeAddView employeeAddView;

    EmployeeAddModel(EmployeeAddView employeeAddView) {
        this.employeeAddView = employeeAddView;
    }

    String validateName(String name) {

        if (name == null || name.trim().isEmpty()) return "Name cannot be empty";
        String trimmed = name.trim();

        if (trimmed.length() < MIN_NAME_LENGTH || trimmed.length() > MAX_NAME_LENGTH) {
            return "Name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters";
        }

        return null;
    }


    String validateEmail(String email) {

        if (email == null || email.trim().isEmpty()) return "Email cannot be empty";
        String trimmed = email.trim();

        if (!EMAIL_PATTERN.matcher(trimmed).matches()) return "Enter a valid email address";
        if (NexFlowDB.getInstance().getEmployeeByEmail(trimmed) != null) {
            return "This email is already registered";
        }

        return null;
    }



    String validatePassword(String accessKey) {

        if (accessKey == null || accessKey.isEmpty()) return "Password cannot be empty";

        if (!PASSWORD_PATTERN.matcher(accessKey).matches()) {
            return "Password must be at least 8 characters and contain letters and numbers";
        }

        return null;
    }



    String validateConfirmPassword(String accessKey, String confirmPassword) {

        if (confirmPassword == null || !confirmPassword.equals(accessKey)) {
            return "Passwords do not match";
        }

        return null;
    }


    boolean isFirstEmployee() {
        return NexFlowDB.getInstance().getEmployees().isEmpty();
    }

    boolean needsReportingManager(Employee.Role role) {
        return role == Employee.Role.EMPLOYEE;
    }


    String validateMobile(String mobile) {

        if (mobile == null || mobile.trim().isEmpty()) return "Mobile number cannot be empty";

        if (!MOBILE_PATTERN.matcher(mobile.trim()).matches()) {
            return "Enter a valid 10 digit mobile number";
        }

        return null;
    }


    Long parseDateOfBirth(String dobText) {

        Long dobMillis = ParserToolkit.parseDate(dobText);

        if (dobMillis == null) return null;
        if (dobMillis >= System.currentTimeMillis()) return null;
        if (ParserToolkit.calculateAgeYears(dobMillis) < MIN_AGE_YEARS) return null;

        return dobMillis;
    }


    Employee.Role parseRole(String choice) {

        if (choice == null) return null;
        String c = choice.trim();

        if (c.equals("1") || c.equalsIgnoreCase("Manager")) return Employee.Role.MANAGER;
        if (c.equals("2") || c.equalsIgnoreCase("Employee")) return Employee.Role.EMPLOYEE;

        return null;
    }


    void addEmployee(String name, String email, String accessKey, String mobile, Long dob, Employee.Role role, Long reportingTo) {

        Employee staffMember = new Employee();

        staffMember.setName(name.trim());
        staffMember.setEmail(email.trim());
        staffMember.setPassword(accessKey);
        staffMember.setMobileNo(mobile.trim());
        staffMember.setDob(dob);
        staffMember.setRole(role);
        staffMember.setReportingTo(reportingTo);
        staffMember.setStatus(Employee.EmployeeStatus.ACTIVE);

        Employee saved = NexFlowDB.getInstance().addEmployee(staffMember);

        if (saved == null) {
            employeeAddView.onEmployeeAddFailed("Could not add staffMember. Please try again.");
            return;
        }

        employeeAddView.onEmployeeAdded(saved);

    }
}
