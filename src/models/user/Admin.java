package models.user;

import models.tour.Tour;

public class Admin extends Person {
    private String adminId;

    public Admin(String name, String email, String phoneNumber, String adminId) {
        super(name, email, phoneNumber);
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String getRole() {
        return "Admin";
    }


    public void addTour(Tour tour) {

    }

    public void removeTour(String tourId) {

    }
}
