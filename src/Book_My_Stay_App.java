import java.util.*;

// Custom Exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType + ", Nights: " + nights;
    }
}

// Hotel Inventory class
class HotelInventory {

    private Map<String, Integer> roomInventory = new HashMap<>();

    public HotelInventory() {
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 1);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        int available = roomInventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        roomInventory.put(roomType, available - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String room : roomInventory.keySet()) {
            System.out.println(room + " Rooms Available: " + roomInventory.get(room));
        }
    }
}

// Validator class
class InvalidBookingValidator {

    public static void validate(String guestName, String roomType, int nights) throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type must be provided.");
        }

        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than zero.");
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        HotelInventory inventory = new HotelInventory();
        List<Reservation> bookingHistory = new ArrayList<>();

        try {

            // Valid booking
            String guestName = "Alice";
            String roomType = "Deluxe";
            int nights = 2;

            InvalidBookingValidator.validate(guestName, roomType, nights);
            inventory.bookRoom(roomType);

            Reservation reservation = new Reservation(guestName, roomType, nights);
            bookingHistory.add(reservation);

            System.out.println("Booking successful: " + reservation);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {

            // Invalid booking (wrong room type)
            String guestName = "Bob";
            String roomType = "Luxury"; // invalid
            int nights = 3;

            InvalidBookingValidator.validate(guestName, roomType, nights);
            inventory.bookRoom(roomType);

            Reservation reservation = new Reservation(guestName, roomType, nights);
            bookingHistory.add(reservation);

            System.out.println("Booking successful: " + reservation);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {

            // Invalid booking (invalid nights)
            String guestName = "Charlie";
            String roomType = "Standard";
            int nights = 0;

            InvalidBookingValidator.validate(guestName, roomType, nights);
            inventory.bookRoom(roomType);

            Reservation reservation = new Reservation(guestName, roomType, nights);
            bookingHistory.add(reservation);

            System.out.println("Booking successful: " + reservation);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        inventory.displayInventory();

        System.out.println("\nConfirmed Booking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }
    }
}