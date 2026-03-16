import java.util.*;


class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (Cost: " + cost + ")";
    }
}


class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


class AddOnServiceManager {


    private Map<String, List<Service>> reservationServices = new HashMap<>();


    public void addService(String reservationId, Service service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }


    public double calculateTotalServiceCost(String reservationId) {
        double total = 0;

        List<Service> services = reservationServices.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

public class Book_My_Stay_App {

    public static void main(String[] args) {


        Reservation reservation = new Reservation("R101", "Arun", "Deluxe");


        AddOnServiceManager serviceManager = new AddOnServiceManager();

        Service breakfast = new Service("Breakfast", 500);
        Service airportPickup = new Service("Airport Pickup", 1200);
        Service spa = new Service("Spa Access", 1500);


        serviceManager.addService(reservation.getReservationId(), breakfast);
        serviceManager.addService(reservation.getReservationId(), airportPickup);
        serviceManager.addService(reservation.getReservationId(), spa);


        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Guest Name: " + reservation.getGuestName());
        System.out.println("Room Type: " + reservation.getRoomType());


        System.out.println("\nSelected Add-On Services:");

        List<Service> services = serviceManager.getServices(reservation.getReservationId());

        for (Service s : services) {
            System.out.println("- " + s);
        }


        double totalCost = serviceManager.calculateTotalServiceCost(reservation.getReservationId());

        System.out.println("\nTotal Add-On Service Cost: " + totalCost);
    }
}