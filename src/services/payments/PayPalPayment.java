package services.payments;

import entities.Booking;
import entities.Payment;

public class PayPalPayment extends Payment {
    private String email;

    public PayPalPayment(long id, Booking booking, double amount, String email) {
        super(id, booking, amount, "PayPal");
        this.email = email;
    }

    @Override
    public boolean processPayment() {

        System.out.println("Processing PayPal payment of " + getAmount() + " for " + email);
        setSuccessful(true);
        return true;
    }

    @Override
    public boolean refundPayment() {
        System.out.println("Refunding PayPal payment of " + getAmount() + " for " + email);
        setSuccessful(false);
        return true;
    }
}
