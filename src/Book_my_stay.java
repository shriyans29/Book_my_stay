abstract class Room {
    protected int numberofbeds;
    protected int squarefeet;
    protected double pricepernight;

    public Room(int numberofbeds,int squarefeet,double pricepernight) {
        this.squarefeet = squarefeet;
        this.numberofbeds = numberofbeds;
        this.pricepernight = pricepernight;
    }

    void display() {
        System.out.println("size: " + squarefeet);
        System.out.println("Beds: " + numberofbeds);
        System.out.println("Price per night: $" + pricepernight);
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super(1, 250, 100);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super(2, 400, 180);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super(3, 750, 350);
    }
}

public class Book_my_stay {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("=== Room Availability ===");

        single.display();
        System.out.println("Available: " + singleAvailable);
        System.out.println();

        dbl.display();
        System.out.println("Available: " + doubleAvailable);
        System.out.println();

        suite.display();
        System.out.println("Available: " + suiteAvailable);
    }
}