import entities.*;
import interfaces.UserInterface;
import services.UserService;
import services.payments.BkashPayment;
import services.payments.CreditCardPayment;
import services.payments.NagadPayment;
import services.payments.PayPalPayment;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private UserInterface userInterface = new UserService();
    private ArrayList<Tour> tours = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();
    private ArrayList<Payment> payments = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();
    private User loggedInUser;
    private long lastTourId = 0;

    private final String dbFolder = "database/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();
        main.initializeData();
        main.mainOptions(scanner);
        scanner.close();
    }

    private void initializeData() {
        loadTours();
        loadMembers();
        loadBookings();
        loadPayments();
    }


    private void loadTours() {
        File file = new File(dbFolder + "tours.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Tour tour = new Tour(
                        Long.parseLong(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        Double.parseDouble(data[4]),
                        data[5].split("\\|")
                );
                tours.add(tour);

                if (tour.getId() > lastTourId) {
                    lastTourId = tour.getId();
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tours: " + e.getMessage());
        }
    }

    // Load members from file
    private void loadMembers() {
        File file = new File(dbFolder + "members.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Member member = new Member(
                        Long.parseLong(data[0]),
                        data[1],
                        data[2],
                        data[3]
                );
                members.add(member);
            }
        } catch (IOException e) {
            System.out.println("Error loading members: " + e.getMessage());
        }
    }

    // Load bookings from file
    private void loadBookings() {
        File file = new File(dbFolder + "bookings.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Member member = findMemberById(Long.parseLong(data[1]));
                Tour tour = findTourById(Long.parseLong(data[2]));
                if (member != null && tour != null) {
                    Booking booking = new Booking(
                            Long.parseLong(data[0]),
                            member,
                            tour
                    );
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }




    private Member findMemberById(long memberId) {
        for (Member member : members) {
            if (member.getId() == memberId) {
                return member;
            }
        }
        return null;
    }

    private Tour findTourById(long tourId) {
        for (Tour tour : tours) {
            if (tour.getId() == tourId) {
                return tour;
            }
        }
        return null;
    }


    // Load payments from file
    private Booking findBookingById(long bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId() == bookingId) {
                return booking;
            }
        }
        return null;
    }

    private void loadPayments() {
        File file = new File(dbFolder + "payments.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Booking booking = findBookingById(Long.parseLong(data[1]));
                double amount = Double.parseDouble(data[2]);
                String method = data[3];

                if (booking == null) {
                    System.out.println("Warning: Booking with ID " + data[1] + " not found for payment " + data[0]);
                    continue;
                }

                Payment payment = null;
                switch (method) {
                    case "CreditCard":
                        payment = new CreditCardPayment(
                                Long.parseLong(data[0]),
                                booking,
                                amount,
                                data[4],
                                data[5],
                                data[6]
                        );
                        break;
                    case "PayPal":
                        payment = new PayPalPayment(
                                Long.parseLong(data[0]),
                                booking,
                                amount,
                                data[4]
                        );
                        break;
                    case "Bkash":
                        payment = new BkashPayment(
                                Long.parseLong(data[0]),
                                booking,
                                amount,
                                data[4]
                        );
                        break;
                    case "Nagad":
                        payment = new NagadPayment(
                                Long.parseLong(data[0]),
                                booking,
                                amount,
                                data[4]
                        );
                        break;
                    default:
                        System.out.println("Invalid payment method for payment ID: " + data[0]);
                        continue;
                }

                payments.add(payment);
            }
        } catch (IOException e) {
            System.out.println("Error loading payments: " + e.getMessage());
        }
    }


    public void addTour(Scanner scanner) {
        try {
            long id = ++lastTourId;
            System.out.println("Tour ID will be automatically assigned: " + id);


            System.out.print("Enter tour title: ");
            String title = scanner.nextLine();

            System.out.print("Enter tour description: ");
            String description = scanner.nextLine();

            System.out.print("Enter tour location: ");
            String location = scanner.nextLine();

            System.out.print("Enter tour price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter available spots (separated by commas): ");
            String spotsInput = scanner.nextLine();
            String[] availableSpots = spotsInput.split(",");


            Tour newTour = new Tour(id, title, description, location, price, availableSpots);


            tours.add(newTour);
            saveTours();

            System.out.println("Tour added successfully: " + newTour);

        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    private void saveTours() {
        try (FileWriter writer = new FileWriter(dbFolder + "tours.txt", true)) { // Append mode
            for (Tour tour : tours) {
                writer.write(tour.getId() + "," + tour.getTitle() + "," + tour.getDescription() + ","
                        + tour.getLocation() + "," + tour.getPrice() + "," + String.join("|", tour.getAvailableSpots()) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tours: " + e.getMessage());
        }
    }

    // Save members to file
    private void saveMembers() {
        try (FileWriter writer = new FileWriter(dbFolder + "members.txt")) {
            for (Member member : members) {
                writer.write(member.getId() + "," + member.getName() + "," + member.getEmail() + "," + member.getPhone() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }

    // Save bookings to file
    private void saveBookings() {
        try (FileWriter writer = new FileWriter(dbFolder + "bookings.txt")) {
            for (Booking booking : bookings) {
                writer.write(booking.getId() + "," + booking.getMember().getId() + "," + booking.getTour().getId() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    // Save payments to file
    private void savePayments() {
        try (FileWriter writer = new FileWriter(dbFolder + "payments.txt")) {
            for (Payment payment : payments) {
                writer.write(payment.getId() + "," + payment.getBooking().getId() + "," + payment.getAmount() + ","
                        + payment.getPaymentMethod() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving payments: " + e.getMessage());
        }
    }

    private void bookTour(Scanner scanner) {
        try {
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
            saveBookings();
            saveMembers();
            bookTour(scanner);
        } catch (Exception e) {
            System.out.println("An error occurred while booking the tour: " + e.getMessage());
        }
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
        try {
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
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
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
        try {
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
            scanner.nextLine();

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
            savePayments();
            System.out.println("Payment of " + amount + " was successful using method " + paymentOption);
        } catch (Exception e) {
            System.out.println("An error occurred during payment: " + e.getMessage());
        }
    }



    private void mainOptions(Scanner scanner) {
        int option = 0;
        while (option != 4) {
            if (loggedInUser != null) {
                System.out.println("\n\nWelcome to the Tour Management System!");
                System.out.println("1. List Tours");
                System.out.println("2. Add New Tour");
                System.out.println("3. Booking");
                System.out.println("4. Payment List");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        listTours();
                        break;
                    case 2:
                        addTour(scanner);
                        break;
                    case 3:
                        bookTour(scanner);
                        break;
                    case 4:
                        viewPayments();
                        break;
                    case 5:
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

    private void viewPayments() {
        if (loggedInUser == null) {
            System.out.println("Please log in to view payments.");
            return;
        }

        if (payments.isEmpty()) {
            System.out.println("No payments found.");
            return;
        }

        System.out.println("Payments List:");
        boolean foundPayments = false;

        for (Payment payment : payments) {
            Booking booking = payment.getBooking();
            Member member = booking.getMember();

            System.out.println("Payment ID: " + payment.getId() +
                    ", Amount: " + payment.getAmount() +
                    ", Payment Method: " + payment.getPaymentMethod() +
                    ", Booking ID: " + booking.getId() +
                    ", Member: " + member.getName() +
                    ", Email: " + member.getEmail());
            foundPayments = true;
        }

        if (!foundPayments) {
            System.out.println("No payments found.");
        }
    }

}
