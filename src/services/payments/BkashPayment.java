package services.payments;

import entities.Booking;
import entities.Payment;
import exceptions.PaymentException;

public class BkashPayment extends Payment {
    private String bkashNumber;

    public BkashPayment(long id, Booking booking, double amount, String bkashNumber) {
        super(id, booking, amount, "Bkash");
        this.bkashNumber = bkashNumber;
    }

    @Override
    public boolean processPayment() throws PaymentException {
        try {
            if (bkashNumber.isEmpty()) {
                throw new PaymentException("Bkash number is invalid.");
            }

            System.out.println("Processing Bkash payment...");
            setSuccessful(true);
            return isSuccessful();
        } catch (PaymentException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An error occurred while processing the Bkash payment: " + e.getMessage());
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