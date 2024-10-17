package services.payments;

import entities.Booking;
import entities.Payment;
import exceptions.PaymentException;

public class PayPalPayment extends Payment {
    private String paypalEmail;

    public PayPalPayment(long id, Booking booking, double amount, String paypalEmail) {
        super(id, booking, amount, "PayPal");
        this.paypalEmail = paypalEmail;
    }

    @Override
    public boolean processPayment()throws PaymentException {
        try {
            if (paypalEmail.isEmpty()) {
                throw new PaymentException("PayPal email is invalid.");
            }

            System.out.println("Processing PayPal payment...");
            setSuccessful(true);
            return isSuccessful();
        } catch (PaymentException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An error occurred while processing the PayPal payment: " + e.getMessage());
            return false;
        }
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
