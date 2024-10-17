import entities.*;
import services.payments.BkashPayment;
import services.payments.CreditCardPayment;
import services.payments.NagadPayment;
import services.payments.PayPalPayment;

import java.util.Scanner;

public class Main {
    private static final int MAX_TOURS = 100;
    private static final int MAX_BOOKINGS = 100;
    private static final int MAX_MEMBERS = 100;
    private static final int MAX_PAYMENTS = 100;

    private static final Tour[] tours = new Tour[MAX_TOURS];
    private static final Booking[] bookings = new Booking[MAX_BOOKINGS];
    private static final Member[] members = new Member[MAX_MEMBERS];
    private static final Payment[] payments = new Payment[MAX_PAYMENTS];

    private static int tourCount = 0;
    private static int bookingCount = 0;
    private static int memberCount = 0;
    private static int paymentCount = 0;

    private static User loggedInUser;

    public static void main(String[] args) {
        initializeData();
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();
        main.mainOptions(scanner);
        scanner.close();
    }

    private static void initializeData() {
        tours[tourCount++] = new Tour(1, "Bandharban", "Explore the mountain.", "Bangladesh", 10000.00, new String[]{"Spot 1", "Spot 2", "Spot 3"});
        tours[tourCount++] = new Tour(2, "Cumilla", "Beautiful city.", "Bangladesh", 500.00, new String[]{"Spot A", "Spot B", "Spot C"});
    }

    private void mainOptions(Scanner scanner) {
        int option = 0;
        while (option != 7) {
            if (loggedInUser != null) {
                System.out.println("\n\nWelcome to the Tour Management System!");
                System.out.println("1. List Tours");
                System.out.println("2. Booking");
                System.out.println("3. View Bookings");
                System.out.println("4. Make a Payment");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                option = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (option) {
                    case 1:
                        listTours();
                        break;
                    case 2:
                        bookTour(scanner);
                        break;
                    case 3:
                        viewBookings();
                        break;
                    case 4:
                        makePayment(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting... Thank you for using the Tour Management System!");
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            } else {
                login(scanner);
            }
        }
    }

    private void listTours() {
        if (loggedInUser == null) {
            System.out.println("Please log in to view tours.");
            return;
        }
        System.out.println("\nAvailable Tours:");
        for (int i = 0; i < tourCount; i++) {
            System.out.println(tours[i]);
        }
    }

    private void bookTour(Scanner scanner) {
        listTours();
        System.out.print("Enter Tour ID to book: ");
        long tourId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Tour selectedTour = null;
        for (int i = 0; i < tourCount; i++) {
            if (tours[i].getId() == tourId) {
                selectedTour = tours[i];
                break;
            }
        }

        if (selectedTour == null) {
            System.out.println("Tour not found.");
            return;
        }

        System.out.print("Enter member name: ");
        String memberName = scanner.nextLine();
        System.out.print("Enter member email: ");
        String memberEmail = scanner.nextLine();
        System.out.print("Enter member phone: ");
        String memberPhone = scanner.nextLine();

        Member member = new Member(memberCount + 1, memberName, memberEmail, memberPhone);
        members[memberCount++] = member;

        Booking booking = new Booking(bookingCount + 1, member, selectedTour);
        bookings[bookingCount++] = booking;

        System.out.println("Booking successful for: " + selectedTour.getTitle());
    }

    private void viewBookings(Scanner scanner) {
        if (loggedInUser == null) {
            System.out.println("Please log in to view bookings.");
            return;
        }

        System.out.print("Enter Tour ID to view bookings: ");
        long tourId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        boolean foundBookings = false;
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getTour().getId() == tourId) {
                Member member = bookings[i].getMember();
                System.out.println("Booking ID: " + bookings[i].getId() + ", Member: " + member.getName() +
                        ", Email: " + member.getEmail());
                foundBookings = true;
            }
        }

        if (!foundBookings) {
            System.out.println("No bookings found for the selected tour.");
        }
    }

    private void makePayment(Scanner scanner) {
        if (loggedInUser == null) {
            System.out.println("Please log in to make a payment.");
            return;
        }

        System.out.print("Enter Booking ID to make a payment: ");
        long bookingId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Booking selectedBooking = null;
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getId() == bookingId) {
                selectedBooking = bookings[i];
                break;
            }
        }

        if (selectedBooking == null) {
            System.out.println("Booking not found.");
            return;
        }

        System.out.println("Choose payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. PayPal");
        System.out.println("3. Bkash");
        System.out.println("4. Nagad");
        int paymentOption = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter payment amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        Payment payment = null;
        switch (paymentOption) {
            case 1:
                System.out.print("Enter card number: ");
                String cardNumber = scanner.nextLine();
                System.out.print("Enter cardholder name: ");
                String cardHolderName = scanner.nextLine();
                System.out.print("Enter expiry date (MM/YY): ");
                String expiryDate = scanner.nextLine();
                payment = new CreditCardPayment(paymentCount + 1, selectedBooking, amount, cardNumber, cardHolderName, expiryDate);
                break;
            case 2:
                System.out.print("Enter PayPal email: ");
                String paypalEmail = scanner.nextLine();
                payment = new PayPalPayment(paymentCount + 1, selectedBooking, amount, paypalEmail);
                break;
            case 3:
                System.out.print("Enter Bkash number: ");
                String bkashNumber = scanner.nextLine();
                payment = new BkashPayment(paymentCount + 1, selectedBooking, amount, bkashNumber);
                break;
            case 4:
                System.out.print("Enter Nagad number: ");
                String nagadNumber = scanner.nextLine();
                payment = new NagadPayment(paymentCount + 1, selectedBooking, amount, nagadNumber);
                break;
            default:
                System.out.println("Invalid payment method.");
                return;
        }

        payments[paymentCount++] = payment;
        System.out.println("Payment successful! Payment ID: " + payment.getId());
    }

    private void login(Scanner scanner) {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (userInterface.validateUser(email, password)) {
            loggedInUser = userInterface.findByEmail(email);
            System.out.println("Login successful! Welcome, " + loggedInUser.getName());
        } else {
            System.out.println("Invalid email or password.");
        }
    }
}
