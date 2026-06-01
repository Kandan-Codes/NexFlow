package com.mani.nexflow.features.signup;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.features.signin.SignInView;
import com.mani.nexflow.util.TerminalReader;
import com.mani.nexflow.util.ParserToolkit;

import java.util.List;
import java.util.Scanner;

public class SignUpView {

    private final SignUpModel signUpModel;
    private final Scanner scanner;

    public SignUpView() {
        this.signUpModel = new SignUpModel(this);
        this.scanner = TerminalReader.getScanner();
    }

    public void init() {
        startSignUp();
    }

    private void startSignUp() {

        System.out.println();
        System.out.println("Create your NexFlow account");

        boolean firstEmployee = signUpModel.isFirstEmployee();

        String name = promptName();
        String email = promptEmail();
        String accessKey = promptPassword();
        String mobile = promptMobile();
        Long dob = promptDob();
        Employee.Role role;
        Long reportingTo;

        if (firstEmployee) {

            System.out.println("As the first user in the system, you will be registered as a Manager.");
            role = Employee.Role.MANAGER;
            reportingTo = null;

        } else {

            role = promptRole();
            reportingTo = (role == Employee.Role.EMPLOYEE) ? promptReportingManager() : null;

        }

        signUpModel.registerEmployee(name, email, accessKey, mobile, dob, role, reportingTo);
    }


    private String promptName() {

        while (true) {

            System.out.print("Enter your full name: ");
            String input = scanner.nextLine();
            String error = signUpModel.validateName(input);
            if (error == null) return input.trim();
            showErrorMessage(error);

        }
    }


    private String promptEmail() {

        while (true) {

            System.out.print("Enter your email: ");
            String input = scanner.nextLine();
            String error = signUpModel.validateEmail(input);
            if (error == null) return input.trim();

            showErrorMessage(error);
        }

    }


    private String promptPassword() {

        while (true) {

            System.out.print("Enter accessKey (minimum 8 characters with letters and numbers): ");
            String input = scanner.nextLine();
            String error = signUpModel.validatePassword(input);

            if (error != null) {
                showErrorMessage(error);
                continue;
            }

            System.out.print("Confirm accessKey: ");
            String confirm = scanner.nextLine();
            String confirmError = signUpModel.validateConfirmPassword(input, confirm);

            if (confirmError != null) {
                showErrorMessage(confirmError);
                continue;
            }

            return input;
        }

    }

    private String promptMobile() {

        while (true) {

            System.out.print("Enter your 10 digit mobile number: ");
            String input = scanner.nextLine();
            String error = signUpModel.validateMobile(input);
            if (error == null) return input.trim();

            showErrorMessage(error);
        }

    }


    private Long promptDob() {

        while (true) {

            System.out.print("Enter your date of birth (dd-MM-yyyy): ");
            String input = scanner.nextLine();
            Long dob = signUpModel.parseDateOfBirth(input);

            if (dob != null) return dob;
            showErrorMessage("Enter a valid date. You must be at least 18 years old.");
        }

    }



    private Employee.Role promptRole() {

        while (true) {

            System.out.println("Select your role:");
            System.out.println("1. Manager");
            System.out.println("2. Employee");
            System.out.print("Choose an option: ");
            String input = scanner.nextLine();
            Employee.Role role = signUpModel.parseRole(input);
            if (role != null) return role;

            showErrorMessage("Select a valid role.");
        }

    }



    private Long promptReportingManager() {

        List<Employee> managers = signUpModel.getActiveManagers();

        if (managers.isEmpty()) {
            System.out.println("No manager is available yet. You can update your reporting manager later.");
            return null;
        }

        while (true) {

            System.out.println("Select your reporting manager:");

            for (int i = 0; i < managers.size(); i++) {
                Employee manager = managers.get(i);
                System.out.println((i + 1) + ". " + manager.getName() + " (" + manager.getEmployeeId() + ")");
            }

            System.out.print("Choose an option: ");
            Integer index = ParserToolkit.parseNonNegativeInt(scanner.nextLine());

            if (index != null && index >= 1 && index <= managers.size()) {
                return managers.get(index - 1).getId();
            }

            showErrorMessage("Select a valid option.");

        }
    }

    void onSignUpSuccessful(Employee staffMember) {

        System.out.println();
        System.out.println("Account created successfully.");
        System.out.println("Your staffMember id is " + staffMember.getEmployeeId() + ".");
        System.out.println("Please sign in to continue.");

        new SignInView().init();
    }


    void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }
}
