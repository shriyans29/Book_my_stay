import java.util.*;

// Reservation class representing a confirmed booking
class Reservation {
    private int reservationId;
    private String customerName;
    private String roomType;
    private int nights;

    public Reservation(int reservationId, String customerName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Customer: " + customerName +
                ", Room Type: " + roomType +
                ", Nights: " + nights;
    }
}

// BookingHistory class storing confirmed reservations
class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Retrieve reservation list
    public List<Reservation> getReservations() {
        return reservations;
    }
}

// BookingReportService class generating reports
class BookingReportService {

    public void generateSummaryReport(List<Reservation> reservations) {
        System.out.println("\n===== Booking Summary Report =====");

        int totalBookings = reservations.size();
        int totalNights = 0;

        for (Reservation r : reservations) {
            totalNights += r.getNights();
        }

        System.out.println("Total Confirmed Bookings: " + totalBookings);
        System.out.println("Total Nights Booked: " + totalNights);
    }

    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n===== Booking History =====");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

// Admin actor reviewing booking history
class Admin {

    public void viewBookingHistory(BookingHistory history, BookingReportService reportService) {
        List<Reservation> reservations = history.getReservations();
        reportService.displayAllBookings(reservations);
        reportService.generateSummaryReport(reservations);
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        Admin admin = new Admin();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation(101, "Alice", "Deluxe", 2);
        Reservation r2 = new Reservation(102, "Bob", "Suite", 3);
        Reservation r3 = new Reservation(103, "Charlie", "Standard", 1);

        // Add to booking history
        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        // Admin reviews booking history and reports
        admin.viewBookingHistory(bookingHistory, reportService);
    }
}
