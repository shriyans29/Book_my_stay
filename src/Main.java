import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BookMyStayApp {

    static class Reservation {
        private String guestName;
        private int numberOfRooms;

        public Reservation(String guestName, int numberOfRooms) {
            this.guestName = guestName;
            this.numberOfRooms = numberOfRooms;
        }

        public String getGuestName() {
            return guestName;
        }

        public int getNumberOfRooms() {
            return numberOfRooms;
        }

        @Override
        public String toString() {
            return "Reservation{Guest='" + guestName + "', Rooms=" + numberOfRooms + "}";
        }
    }

    private Queue<Reservation> bookingQueue;

    public BookMyStayApp() {
        bookingQueue = new LinkedList<>();
    }

    public void submitBookingRequest(String guestName, int rooms) {
        Reservation reservation = new Reservation(guestName, rooms);
        bookingQueue.add(reservation);
        System.out.println("Booking request added to queue: " + reservation);
    }

    public void displayQueuedRequests() {
        if (bookingQueue.isEmpty()) {
            System.out.println("No booking requests in the queue.");
        } else {
            System.out.println("\nQueued Booking Requests (FIFO order):");
            for (Reservation r : bookingQueue) {
                System.out.println(r);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookMyStayApp app = new BookMyStayApp();

        System.out.println("=== Welcome to Book My Stay App ===");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Submit Booking Request");
            System.out.println("2. View Booking Queue");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter guest name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter number of rooms: ");
                    int rooms = scanner.nextInt();
                    scanner.nextLine();
                    app.submitBookingRequest(name, rooms);
                    break;

                case 2:
                    app.displayQueuedRequests();
                    break;

                case 3:
                    running = false;
                    System.out.println("Exiting Book My Stay App. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}