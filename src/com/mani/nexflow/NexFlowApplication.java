package com.mani.nexflow;

import com.mani.nexflow.features.signin.SignInView;
import com.mani.nexflow.features.signup.SignUpView;
import com.mani.nexflow.util.TerminalReader;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class NexFlowApplication {

    public static final int VERSION_NO = 2;
    public static final String VERSION_NAME = "1.0.0";

    static void main() {

        System.out.println("Welcome to NexFlow");
        System.out.println("Version " + VERSION_NAME);
        showLandingMenu();

    }

    private static void showLandingMenu() {

        Scanner scanner = TerminalReader.getScanner();

        while (true) {

            System.out.println();
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    new SignUpView().init();
                    break;

                case "2":
                    new SignInView().init();
                    break;

                case "3":
                    System.out.println("Thank you for using NexFlow");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}