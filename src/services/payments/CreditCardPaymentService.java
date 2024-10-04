package services.payments;

import repositoryInterfaces.PaymentInterface;

public class CreditCardPaymentService implements PaymentInterface {
    private String cardNumber;

    public CreditCardPaymentService(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public double processPayment(double amount) {

        System.out.println("Credit Card Payment Done. Your card number is: " + cardNumber);
        return amount;
    }
}
