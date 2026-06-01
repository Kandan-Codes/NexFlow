package com.mani.nexflow.features.signin;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.LoginRequest;
import com.mani.nexflow.features.dashboard.HomeView;
import com.mani.nexflow.features.signup.SignUpView;
import com.mani.nexflow.util.TerminalReader;

import java.util.Scanner;

public class SignInView {

    private final SignInModel signInModel;
    private final Scanner scanner;
    private boolean authenticated;

    public SignInView() {

        this.signInModel = new SignInModel(this);
        this.scanner = TerminalReader.getScanner();
        this.authenticated = false;

    }


    public void init() {

        System.out.println();
        System.out.println("Sign in to NexFlow");

        while (!authenticated) {
            promptAndAuthenticate();
            if (authenticated) return;
            if (!promptPostFailureAction()) return;
        }

    }


    private void promptAndAuthenticate() {

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your accessKey: ");
        String accessKey = scanner.nextLine();

        LoginRequest request = new LoginRequest();
        request.setEmail(email == null ? null : email.trim());
        request.setPassword(accessKey);

        signInModel.authenticate(request);
    }


    private boolean promptPostFailureAction() {

        while (true) {

            System.out.println();
            System.out.println("1. Retry");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    return true;
                case "2":
                    new SignUpView().init();
                    return false;
                case "3":
                    System.out.println("Thank you for using NexFlow");
                    System.exit(0);
                    return false;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    void onSignInSuccessful(Employee staffMember) {

        authenticated = true;
        System.out.println("Welcome, " + staffMember.getName());

        new HomeView(staffMember).init();
    }


    void onSignInFailed(String message) {
        System.out.println(message);
    }


    void showErrorMessage(String message) {
        System.out.println(message);
    }
}
