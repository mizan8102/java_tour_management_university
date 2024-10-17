package services.payments;

import entities.Booking;
import entities.Payment;
import exceptions.PaymentException;

public class NagadPayment extends Payment {
    private String nagadNumber;

    public NagadPayment(long id, Booking booking, double amount, String nagadNumber) {
        super(id, booking, amount, "Nagad");
        this.nagadNumber = nagadNumber;
    }

    @Override
    public boolean processPayment()throws PaymentException {
        try {
            if (nagadNumber.isEmpty()) {
                throw new PaymentException("Nagad number is invalid.");
            }

            System.out.println("Processing Nagad payment...");
            setSuccessful(true);
            return isSuccessful();
        } catch (PaymentException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("An error occurred while processing the Nagad payment: " + e.getMessage());
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