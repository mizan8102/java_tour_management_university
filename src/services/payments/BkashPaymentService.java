package services.payments;

import repositoryInterfaces.PaymentInterface;

public class BkashPaymentService implements PaymentInterface {

    private String mobilNumber;
    public BkashPaymentService(String mobilNumber) {
        this.mobilNumber = mobilNumber;
    }
    @Override
    public double processPayment(double amount) {
        System.out.println("Bkash  Payment Done. Your card number is: " + mobilNumber);
        return amount;
    }
}
