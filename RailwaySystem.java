import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class RailwaySystem {
    private static List<Train> trains = new ArrayList<>();
    private static List<Station> stations = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static int bookingIdCounter = 1;

    public static void main(String[] args) {
        initializeData();
        loadBookingsFromFile();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nRailway Registration System");
            System.out.println("1. Book Seat");
            System.out.println("2. Train Path");
            System.out.println("3. Check Info");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    bookSeat(sc);
                    break;
                case 2:
                    seeStationDetails(sc);
                    break;
                case 3:
                    checkInfo(sc);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void initializeData() {
        // Sample stations
        stations.add(new Station("Delhi", "North India"));
        stations.add(new Station("Mumbai", "West India"));
        stations.add(new Station("Chennai", "South India"));
        stations.add(new Station("Kolkata", "East India"));

        // Sample trains 1-4
        List<String> route1 = new ArrayList<>();
        route1.add("Delhi");
        route1.add("Mumbai");
        List<String> categories1 = new ArrayList<>();
        categories1.add("AC");
        categories1.add("Non-AC");
        trains.add(new Train(1, route1, categories1));

        List<String> route2 = new ArrayList<>();
        route2.add("Delhi");
        route2.add("Kolkata");
        List<String> categories2 = new ArrayList<>();
        categories2.add("AC");
        categories2.add("Sleeper");
        trains.add(new Train(2, route2, categories2));

        List<String> route3 = new ArrayList<>();
        route3.add("Mumbai");
        route3.add("Chennai");
        List<String> categories3 = new ArrayList<>();
        categories3.add("AC");
        categories3.add("Non-AC");
        trains.add(new Train(3, route3, categories3));

        List<String> route4 = new ArrayList<>();
        route4.add("Kolkata");
        route4.add("Chennai");
        List<String> categories4 = new ArrayList<>();
        categories4.add("Sleeper");
        categories4.add("General");
        trains.add(new Train(4, route4, categories4));

        // Additional trains
        trains.add(new Train(101, route1, categories1));
        trains.add(new Train(102, route2, categories2));
    }

    private static void saveBookingsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt"))) {
            for (Booking b : bookings) {
                writer.write(b.getBookingId() + "|" + b.getPassenger().getName() + "|" + b.getPassenger().getAge() + "|" + b.getPassenger().getContact() + "|"
                        + b.getTrain().getNumber() + "|" + b.getCategory() + "|" + b.getSeatNumber() + "|" + b.getJourneyStations().get(0) + "|" + b.getJourneyStations().get(1));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    private static void loadBookingsFromFile() {
        File file = new File("bookings.txt");
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 9) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String contact = parts[3];
                    int trainNum = Integer.parseInt(parts[4]);
                    String category = parts[5];
                    int seat = Integer.parseInt(parts[6]);
                    String from = parts[7];
                    String to = parts[8];

                    Train train = null;
                    for (Train t : trains) {
                        if (t.getNumber() == trainNum) {
                            train = t;
                            break;
                        }
                    }
                    if (train != null && train.getCategories().contains(category)) {
                        Person p = new Person(name, age, contact);
                        List<String> journey = new ArrayList<>();
                        journey.add(from);
                        journey.add(to);
                        Booking b = new Booking(id, p, train, category, seat, journey);
                        bookings.add(b);
                        if (id >= bookingIdCounter) {
                            bookingIdCounter = id + 1;
                        }
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }

    private static void bookSeat(Scanner sc) {
        System.out.print("Enter train number: ");
        int trainNum = sc.nextInt();
        sc.nextLine();
        Train selectedTrain = null;
        for (Train t : trains) {
            if (t.getNumber() == trainNum) {
                selectedTrain = t;
                break;
            }
        }
        if (selectedTrain == null) {
            System.out.println("Train not found.");
            return;
        }
        System.out.println("Available categories: " + selectedTrain.getCategories());
        System.out.print("Enter category: ");
        String category = sc.nextLine();
        if (!selectedTrain.getCategories().contains(category)) {
            System.out.println("Category not available.");
            return;
        }
        System.out.print("Enter passenger name: ");
        String name = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter contact: ");
        String contact = sc.nextLine();
        Person passenger = new Person(name, age, contact);
        System.out.print("Enter from station: ");
        String from = sc.nextLine();
        System.out.print("Enter to station: ");
        String to = sc.nextLine();
        List<String> journey = new ArrayList<>();
        journey.add(from);
        journey.add(to);
        int seatNumber = bookingIdCounter; // simple seat assignment
        Booking booking = new Booking(bookingIdCounter++, passenger, selectedTrain, category, seatNumber, journey);
        bookings.add(booking);
        saveBookingsToFile();
        System.out.println("Booking successful! Booking ID: " + booking.getBookingId());
    }

    private static void seeStationDetails(Scanner sc) {
        System.out.print("Enter booking ID: ");
        String idInput = sc.nextLine();
        int id;
        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid booking ID. Please enter a number.");
            return;
        }
        Booking selectedBooking = null;
        for (Booking b : bookings) {
            if (b.getBookingId() == id) {
                selectedBooking = b;
                break;
            }
        }
        if (selectedBooking == null) {
            System.out.println("No booking found with ID " + id);
            return;
        }
        System.out.println("Train Path for Booking ID " + id + ": ");
        selectedBooking.getTrain().displayInfo();
    }

    private static void checkInfo(Scanner sc) {
        System.out.print("Enter booking ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Booking details for ID " + id + ":");
        boolean found = false;
        for (Booking b : bookings) {
            if (b.getBookingId() == id) {
                System.out.println("Booking ID: " + b.getBookingId());
                System.out.println("Train Number: " + b.getTrain().getNumber());
                System.out.println("Person Name: " + b.getPassenger().getName());
                System.out.println("Age: " + b.getPassenger().getAge());
                System.out.println("Contact: " + b.getPassenger().getContact());
                System.out.println("Seat Number: " + b.getSeatNumber());
                System.out.println("Category: " + b.getCategory());
                System.out.println("Journey: " + b.getJourneyStations().get(0) + " to " + b.getJourneyStations().get(1));
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No booking found with ID " + id);
        }
    }


}
