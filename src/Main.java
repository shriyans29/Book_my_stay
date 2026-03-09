import java.util.*;

class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> rooms;

    public SearchService(RoomInventory inventory, Map<String, Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void searchAvailableRooms() {
        for (Map.Entry<String, Integer> entry : inventory.getAllAvailability().entrySet()) {
            if (entry.getValue() > 0) {
                Room r = rooms.get(entry.getKey());
                if (r != null) {
                    System.out.println(r.getType() + " " + r.getPrice() + " " + r.getAmenities() + " Available:" + entry.getValue());
                }
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Standard", 10);
        inventory.addRoomType("Deluxe", 5);
        inventory.addRoomType("Suite", 0);

        Map<String, Room> rooms = new HashMap<>();
        rooms.put("Standard", new Room("Standard", 2000, "WiFi TV"));
        rooms.put("Deluxe", new Room("Deluxe", 3500, "WiFi TV AC"));
        rooms.put("Suite", new Room("Suite", 6000, "WiFi TV AC Jacuzzi"));

        SearchService searchService = new SearchService(inventory, rooms);
        searchService.searchAvailableRooms();
    }
}