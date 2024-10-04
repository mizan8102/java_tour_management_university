package models.tour;

import models.user.Customer;

import java.util.Date;

public class Booking {
    private String bookingId;
    private Tour tour;
    private Customer customer;
    private Date bookingDate;
    private final double totalPrice;

    public Booking(String bookingId, Tour tour, Customer customer, Date bookingDate) {
        this.bookingId = bookingId;
        this.tour = tour;
        this.customer = customer;
        this.bookingDate = bookingDate;
        this.totalPrice = calculateTotalPrice();
    }


    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


    private double calculateTotalPrice() {
        return this.tour.getPrice();
    }
}
