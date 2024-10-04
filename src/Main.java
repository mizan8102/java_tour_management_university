import models.tour.Booking;
import models.tour.Tour;
import models.user.Customer;
import models.user.TourGuide;
import repositoryInterfaces.PaymentInterface;
import services.payments.BkashPaymentService;
import services.payments.CreditCardPaymentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();


        TourGuide guide1 = new TourGuide("Mizanur Rahman", "mizan@gmail.com", "01545315223", "H84H3", "Bangla");
        TourGuide guide2 = new TourGuide("Rana", "rana@gmail.com", "01545315222", "G10r02", "English");


        List<TourGuide> guides = new ArrayList<>();
        guides.add(guide1);
        guides.add(guide2);

        Tour cityTour = new Tour("T001", "Cox's Bazar Tour", "Nice tour", 10000.0, guides);


        Customer customer = new Customer(customerName, "tt@example.com", "01645316379", "C1001");


        Booking booking = new Booking("B001", cityTour, customer, new Date());

        System.out.println("Select payment method: \n 1. Bkash 2. Credit Card \n Enter option : ");
        String method = scanner.nextLine();
        switch (method) {
            case "1":
                System.out.print("Enter Bkash phone number: ");
                String paymentPhoneNumber = scanner.next();
                PaymentInterface bkashPaymentService = new BkashPaymentService(paymentPhoneNumber);
                double amount = bkashPaymentService.processPayment(booking.getTotalPrice());
                System.out.println("\n Booked Successfully");
                break;

            case "2":
                System.out.print("Enter phone number for Credit Card: ");
                String cardNumber = scanner.next();
                PaymentInterface creditCardPaymentService = new CreditCardPaymentService(cardNumber);
                double card_amount = creditCardPaymentService.processPayment(booking.getTotalPrice());
                System.out.println("\n Booked Successfully");
                break;

            default:
                System.out.println("Invalid payment method");
        }



    }
}