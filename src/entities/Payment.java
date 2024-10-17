package entities;

import exceptions.PaymentException;

public abstract class Payment {
    private long id;
    private Booking booking;
    private double amount;
    private String paymentMethod;
    private boolean isSuccessful;

    public Payment(long id, Booking booking, double amount, String paymentMethod) {
        this.id = id;
        this.booking = booking;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.isSuccessful = false;
    }


    public abstract boolean processPayment() throws PaymentException;


    public abstract boolean refundPayment();

    public long getId() {
        return id;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    protected void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    @Override
    public String toString() {
        return "Payment of " + amount + " for " + booking.toString() + " via " + paymentMethod + (isSuccessful ? " (Successful)" : " (Failed)");
    }
}