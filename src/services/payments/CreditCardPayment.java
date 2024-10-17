package services.payments;

import entities.Booking;
import entities.Payment;

public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;

    public CreditCardPayment(long id, Booking booking, double amount, String cardNumber, String cardHolderName, String expirationDate) {
        super(id, booking, amount, "Credit Card");
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean processPayment() {
        System.out.println("Processing Credit Card payment of " + getAmount() + " for " + cardHolderName);

        setSuccessful(true);
        return true; // Indicate success
    }

    @Override
    public boolean refundPayment() {

        System.out.println("Refunding Credit Card payment of $" + getAmount() + " for " + cardHolderName);
        setSuccessful(false);
        return true;
    }
}
