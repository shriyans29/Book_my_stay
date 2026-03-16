import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private int reservationId;
    private String guestName;
    private String roomType;

    public Reservation(int reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// System state that will be persisted
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

// Persistence service
class PersistenceService {

    private static final String FILE_NAME = "hotel_system_state.dat";

    // Save system state
    public static void saveState(SystemState state) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load system state
    public static SystemState loadState() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state. Starting with safe defaults.");
        }

        return null;
    }
}

// Main program
public class BookMyStayApp {

    public static void main(String[] args) {

        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;

        // Attempt to restore previous state
        SystemState restoredState = PersistenceService.loadState();

        if (restoredState != null) {
            inventory = restoredState.inventory;
            bookingHistory = restoredState.bookingHistory;
        } else {
            // Initialize default state
            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);
            inventory.put("Suite", 1);

            bookingHistory = new ArrayList<>();
        }

        // Display restored inventory
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }

        // Simulate a new booking
        System.out.println("\nProcessing new booking...");

        if (inventory.get("Standard") > 0) {

            Reservation r =
                    new Reservation(101 + bookingHistory.size(), "Alice", "Standard");

            bookingHistory.add(r);
            inventory.put("Standard", inventory.get("Standard") - 1);

            System.out.println("Booking Confirmed: " + r);
        } else {
            System.out.println("No Standard rooms available.");
        }

        // Display booking history
        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        // Save state before shutdown
        SystemState state = new SystemState(inventory, bookingHistory);
        PersistenceService.saveState(state);

        System.out.println("\nSystem shutting down...");
    }
}