package com.mani.nexflow.features.signin;

import com.mani.nexflow.data.dto.Employee;
import com.mani.nexflow.data.dto.LoginRequest;
import com.mani.nexflow.data.repository.NexFlowDB;

import java.util.regex.Pattern;

class SignInModel {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final SignInView signInView;

    SignInModel(SignInView signInView) {
        this.signInView = signInView;
    }

    String validateEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return "Email cannot be empty";
        }

        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return "Enter a valid email address";
        }

        return null;
    }



    String validatePassword(String accessKey) {

        if (accessKey == null || accessKey.isEmpty()) {
            return "Password cannot be empty";
        }

        return null;
    }



    void authenticate(LoginRequest request) {

        if (request == null) {
            signInView.onSignInFailed("Invalid email or accessKey");
            return;
        }

        String emailError = validateEmail(request.getEmail());
        if (emailError != null) {
            signInView.onSignInFailed(emailError);
            return;
        }

        String passwordError = validatePassword(request.getPassword());
        if (passwordError != null) {
            signInView.onSignInFailed(passwordError);
            return;
        }

        Employee staffMember = NexFlowDB.getInstance().authenticateEmployee(request.getEmail(), request.getPassword());

        if (staffMember == null) {
            signInView.onSignInFailed("Invalid email or accessKey");
            return;
        }
        if (staffMember.getStatus() == Employee.EmployeeStatus.INACTIVE) {
            signInView.onSignInFailed("Your account is not active. Contact your administrator.");
            return;
        }

        signInView.onSignInSuccessful(staffMember);
    }
}
