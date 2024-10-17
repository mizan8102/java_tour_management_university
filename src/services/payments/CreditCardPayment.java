package services.payments;

import entities.Booking;

import entities.Payment;
import exceptions.PaymentException;

public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;

    public CreditCardPayment(long id, Booking booking, double amount, String cardNumber, String cardHolderName, String expiryDate) {
        super(id, booking, amount, "Credit Card");
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean processPayment() throws PaymentException {

        if (cardNumber == null || cardHolderName == null || expiryDate == null ||
                cardNumber.isEmpty() || cardHolderName.isEmpty() || expiryDate.isEmpty()) {
            throw new PaymentException("Credit card information is incomplete.");
        }

        boolean paymentSuccess = true;

        if (!paymentSuccess) {
            throw new PaymentException("Credit card payment failed.");
        }

        setSuccessful(true);
        return isSuccessful();
    }

    @Override
    public boolean refundPayment() {
        if (isSuccessful()) {
            setSuccessful(false);
            return true;
        }
        return false;
    }
}