import java.util.List;

// Abstraction: Abstract class hiding implementation details
public abstract class Transport {
    protected int number;
    protected List<String> route;

    public Transport(int number, List<String> route) {
        this.number = number;
        this.route = route;
    }

    public abstract void displayInfo();

    public int getNumber() {
        return number;
    }

    public List<String> getRoute() {
        return route;
    }
}
