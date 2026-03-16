import java.util.*;

// Custom exception for booking related errors
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private int reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(int reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Room ID: " + roomId +
                ", Status: " + (active ? "CONFIRMED" : "CANCELLED");
    }
}

// Inventory management
class HotelInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public HotelInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void allocateRoom(String roomType) throws BookingException {

        if (!inventory.containsKey(roomType)) {
            throw new BookingException("Invalid room type.");
        }

        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new BookingException("No rooms available for type: " + roomType);
        }

        inventory.put(roomType, available - 1);
    }

    public void releaseRoom(String roomType) {
        int available = inventory.get(roomType);
        inventory.put(roomType, available + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}

// Cancellation service using Stack for rollback
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelReservation(int reservationId,
                                  Map<Integer, Reservation> bookingHistory,
                                  HotelInventory inventory) throws BookingException {

        if (!bookingHistory.containsKey(reservationId)) {
            throw new BookingException("Reservation does not exist.");
        }

        Reservation reservation = bookingHistory.get(reservationId);

        if (!reservation.isActive()) {
            throw new BookingException("Reservation already cancelled.");
        }

        // Record room id for rollback tracking
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory
        inventory.releaseRoom(reservation.getRoomType());

        // Mark reservation cancelled
        reservation.cancel();

        System.out.println("Cancellation successful for reservation: " + reservationId);
        System.out.println("Released Room ID: " + rollbackStack.pop());
    }
}

// Main class
public class BookMyStayApp{

    public static void main(String[] args) {

        HotelInventory inventory = new HotelInventory();
        Map<Integer, Reservation> bookingHistory = new HashMap<>();
        CancellationService cancellationService = new CancellationService();

        try {

            // Simulate confirmed bookings
            inventory.allocateRoom("Standard");
            Reservation r1 = new Reservation(101, "Alice", "Standard", "S1");
            bookingHistory.put(101, r1);

            inventory.allocateRoom("Deluxe");
            Reservation r2 = new Reservation(102, "Bob", "Deluxe", "D1");
            bookingHistory.put(102, r2);

            System.out.println("Confirmed Bookings:");
            for (Reservation r : bookingHistory.values()) {
                System.out.println(r);
            }

            // Guest cancels a booking
            cancellationService.cancelReservation(101, bookingHistory, inventory);

            // Attempt duplicate cancellation
            cancellationService.cancelReservation(101, bookingHistory, inventory);

        } catch (BookingException e) {
            System.out.println("Operation failed: " + e.getMessage());
        }

        inventory.displayInventory();

        System.out.println("\nUpdated Booking History:");
        for (Reservation r : bookingHistory.values()) {
            System.out.println(r);
        }
    }
}