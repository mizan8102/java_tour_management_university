import entities.*;
import interfaces.UserInterface;
import services.UserService;
import services.payments.BkashPayment;
import services.payments.CreditCardPayment;
import services.payments.NagadPayment;
import services.payments.PayPalPayment;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private UserInterface userInterface = new UserService();
    private ArrayList<Tour> tours = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();
    private ArrayList<Payment> payments = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();
    private User loggedInUser;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();
        main.initializeData();
        main.mainOptions(scanner);
        scanner.close();
    }

    private void bookTour(Scanner scanner) {
        if (tours.isEmpty()) {
            System.out.println("No tours available to book.");
            return;
        }
        System.out.println("\n --------------------------- Booking System ------------------------ \n");
        System.out.println("1. Show previous booking member list");
        System.out.println("2. Add a new member");
        System.out.print("Select an option (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            showPreviousBookings(scanner);
            return;
        } else if (choice != 2) {
            System.out.println("Invalid option. Please try again.");
            return;
        }

        listTours();
        System.out.print("Enter Tour ID to book: ");
        long tourId = scanner.nextLong();
        scanner.nextLine();

        Tour selectedTour = null;
        for (Tour tour : tours) {
            if (tour.getId() == tourId) {
                selectedTour = tour;
                break;
            }
        }

        if (selectedTour == null) {
            System.out.println("Tour with ID " + tourId + " not found.");
            return;
        }

        System.out.print("Enter member name: ");
        String memberName = scanner.nextLine();
        System.out.print("Enter member email: ");
        String memberEmail = scanner.nextLine();
        System.out.print("Enter member phone: ");
        String memberPhone = scanner.nextLine();


        Member member = new Member(members.size() + 1, memberName, memberEmail, memberPhone);
        members.add(member);

        Booking newBooking = new Booking(bookings.size() + 1, member, selectedTour);
        bookings.add(newBooking);
        makePayment(scanner, newBooking);
        System.out.println("Tour booked successfully: " + selectedTour.getTitle());
        bookTour(scanner);
    }

    private void showPreviousBookings(Scanner scanner) {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        System.out.println("Available Tours:");
        System.out.println("---------------------------------------------------------");
        for (Tour tour : tours) {
            System.out.println("Tour ID: " + tour.getId() + ", Title: " + tour.getTitle());
        }

        // Prompt user to select a tour
        System.out.print("Select a Tour ID to view bookings: ");
        long selectedTourId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Tour selectedTour = null;
        for (Tour tour : tours) {
            if (tour.getId() == selectedTourId) {
                selectedTour = tour;
                break;
            }
        }

        if (selectedTour == null) {
            System.out.println("Tour with ID " + selectedTourId + " not found.");
            return;
        }

        System.out.println("Previous Bookings for Tour: " + selectedTour.getTitle());
        System.out.println("---------------------------------------------------------");
        boolean hasBookings = false;

        for (Booking booking : bookings) {
            if (booking.getTour().getId() == selectedTourId) {
                hasBookings = true;
                Member member = booking.getMember();
                System.out.println("Booking ID: " + booking.getId() + ", Member: " + member.getName() +
                        ", Email: " + member.getEmail());
            }
        }

        if (!hasBookings) {
            System.out.println("No bookings found for the selected tour.");
        }
    }


    private void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\nYour Bookings:");
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    private void login(Scanner scanner) {
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        if (userInterface.validateUser(email, password)) {
            loggedInUser = userInterface.findByEmail(email);
            System.out.println("Login successful! Welcome, " + loggedInUser.getName());
        } else {
            System.out.println("Invalid email or password. Try again.");
        }
    }

    private void listTours() {
        if (loggedInUser == null) {
            System.out.println("Please log in to view tours.");
            return;
        }
        System.out.println("\nAvailable Tours:");
        System.out.println("-----------------------------------------------------------------------------------");
        for (Tour tour : tours) {
            System.out.println(tour);
        }
    }

    private void makePayment(Scanner scanner, Booking selectedBooking) {
        if (loggedInUser == null) {
            System.out.println("Please log in to make a payment.");
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
        scanner.nextLine();



        if (selectedBooking == null) {
            System.out.println("Booking with ID not found.");
            return;
        }

        Payment payment = null;

        switch (paymentOption) {
            case 1:
                System.out.print("Enter card number: ");
                String cardNumber = scanner.next();
                System.out.print("Enter cardholder name: ");
                String cardHolderName = scanner.next();
                System.out.print("Enter expiry date (MM/YY): ");
                String expiryDate = scanner.next();
                payment = new CreditCardPayment(payments.size() + 1, selectedBooking, amount, cardNumber, cardHolderName, expiryDate);
                break;
            case 2:
                System.out.print("Enter PayPal email: ");
                String paypalEmail = scanner.next();
                payment = new PayPalPayment(payments.size() + 1, selectedBooking, amount, paypalEmail);
                break;
            case 3:
                System.out.print("Enter Bkash number: ");
                String bkashNumber = scanner.next();
                payment = new BkashPayment(payments.size() + 1, selectedBooking, amount, bkashNumber);
                break;
            case 4:
                System.out.print("Enter Nagad number: ");
                String nagadNumber = scanner.next();
                payment = new NagadPayment(payments.size() + 1, selectedBooking, amount, nagadNumber);
                break;
            default:
                System.out.println("Invalid payment option. Payment failed.");
                return;
        }

        payments.add(payment);
        System.out.println("Payment of " + amount + " was successful using " + paymentOption);
    }

    private void initializeData() {

        tours.add(new Tour(1, "Bandharban", "Explore the mountain.", "Bangladesh", 10000.00, new String[]{"Spot 1", "Spot 2", "Spot 3"}));
        tours.add(new Tour(2, "Cumilla", "Beautiful city.", "Bangladesh", 500.00, new String[]{"Spot A", "Spot B", "Spot C"}));
    }

    private void mainOptions(Scanner scanner) {
        int option = 0;
        while (option != 4) {
            if (loggedInUser != null) {
                System.out.println("\n\nWelcome to the Tour Management System!");
                System.out.println("1. List Tours");
                System.out.println("2. Booking");
                System.out.println("3. Payment List");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listTours();
                        break;
                    case 2:
                        bookTour(scanner);
                        break;
                    case 3:

                        break;
                    case 4:
                        System.out.println("Exiting... Thank you for using the Tour Management System!");
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            } else {
                System.out.println("\n -------------------------------------- LOG IN PORTAL -----------------------------------");
                login(scanner);
            }
        }
    }
}
