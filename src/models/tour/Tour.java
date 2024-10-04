package models.tour;

import models.user.Customer;
import models.user.TourGuide;

import java.util.List;

public class Tour {
    private String tourId;
    private String tourName;
    private String description;
    private double price;
    private List<TourGuide> tourGuides;
    private List<Customer> customers;

    public Tour(String tourId, String tourName, String description, double price, List<TourGuide> tourGuides) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.description = description;
        this.price = price;
        this.tourGuides = tourGuides;
    }


    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<TourGuide> getTourGuides() {
        return tourGuides;
    }

    public void setTourGuides(List<TourGuide> tourGuides) {
        this.tourGuides = tourGuides;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
