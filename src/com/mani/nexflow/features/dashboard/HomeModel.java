package com.mani.nexflow.features.dashboard;

import com.mani.nexflow.data.dto.Employee;

class HomeModel {

    private final HomeView homeView;


    HomeModel(HomeView homeView) {
        this.homeView = homeView;
    }


    void init(Employee staffMember) {

        if (staffMember == null || staffMember.getRole() == null) {
            homeView.showUnauthorized();
            return;
        }

        if (staffMember.getRole() == Employee.Role.MANAGER) {

            homeView.showManagerMenu();

        } else {

            homeView.showEmployeeMenu();

        }
    }
}