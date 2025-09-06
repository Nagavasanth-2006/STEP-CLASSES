class MovieTicket {
    String movieName;
    String theatreName;
    int seatNumber;
    double price;

    MovieTicket() {
        this.movieName = "Unknown";
        this.theatreName = "Unknown";
        this.seatNumber = -1;
        this.price = 0.0;
    }

    MovieTicket(String movieName) {
        this.movieName = movieName;
        this.theatreName = "Unknown";
        this.seatNumber = -1;
        this.price = 200.0;
    }

    MovieTicket(String movieName, int seatNumber) {
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.theatreName = "PVR";
        this.price = 200.0;
    }

    MovieTicket(String movieName, String theatreName, int seatNumber, double price) {
        this.movieName = movieName;
        this.theatreName = theatreName;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    void printTicket() {
        System.out.println("Movie: " + movieName);
        System.out.println("Theatre: " + theatreName);
        System.out.println("Seat Number: " + seatNumber);
        System.out.println("Price: " + price);
        System.out.println("-------------------");
    }
}

public class MovieTicketBookingSystem {
    public static void main(String[] args) {
        MovieTicket t1 = new MovieTicket();
        MovieTicket t2 = new MovieTicket("Avengers");
        MovieTicket t3 = new MovieTicket("Inception", 42);
        MovieTicket t4 = new MovieTicket("Interstellar", "INOX", 15, 350);

        t1.printTicket();
        t2.printTicket();
        t3.printTicket();
        t4.printTicket();
    }
}
