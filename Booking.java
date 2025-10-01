import java.util.List;

public class Booking {
    // Encapsulation: Private fields to hide data
    private int bookingId;
    private Person passenger;
    private Train train;
    private String category;
    private int seatNumber;
    private List<String> journeyStations; // stations for this booking

    public Booking(int bookingId, Person passenger, Train train, String category, int seatNumber, List<String> journeyStations) {
        this.bookingId = bookingId;
        this.passenger = passenger;
        this.train = train;
        this.category = category;
        this.seatNumber = seatNumber;
        this.journeyStations = journeyStations;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Person getPassenger() {
        return passenger;
    }

    public void setPassenger(Person passenger) {
        this.passenger = passenger;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public List<String> getJourneyStations() {
        return journeyStations;
    }

    public void setJourneyStations(List<String> journeyStations) {
        this.journeyStations = journeyStations;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + ", Passenger: " + passenger + ", Train: " + train.getNumber() +
                ", Category: " + category + ", Seat: " + seatNumber + ", Journey: " + journeyStations;
    }
}
