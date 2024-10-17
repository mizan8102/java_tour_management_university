package services.payments;

import entities.Booking;
import entities.Payment;

public class BkashPayment extends Payment {
    private String bkashNumber;

    public BkashPayment(long id, Booking booking, double amount, String bkashNumber) {
        super(id, booking, amount, "Bkash");
        this.bkashNumber = bkashNumber;
    }

    @Override
    public boolean processPayment() {
        System.out.println("Processing Bkash payment of " + getAmount() + " for " + bkashNumber);
        setSuccessful(true);
        return true;
    }

    @Override
    public boolean refundPayment() {
        System.out.println("Refunding Bkash payment of " + getAmount() + " for " + bkashNumber);
        setSuccessful(false);
        return true;
    }
}