package services.payments;

import entities.Booking;
import entities.Payment;

public class NagadPayment extends Payment {
    private String nagadNumber;

    public NagadPayment(long id, Booking booking, double amount, String nagadNumber) {
        super(id, booking, amount, "Nagad");
        this.nagadNumber = nagadNumber;
    }

    @Override
    public boolean processPayment() {
        System.out.println("Processing Nagad payment of " + getAmount() + " for " + nagadNumber);
        setSuccessful(true);
        return true;
    }

    @Override
    public boolean refundPayment() {
        System.out.println("Refunding Nagad payment of " + getAmount() + " for " + nagadNumber);
        setSuccessful(false);
        return true;
    }
}