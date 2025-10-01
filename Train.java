import java.util.List;

// Inheritance: Train inherits from Transport
public class Train extends Transport {
    private List<String> categories; // e.g., "AC", "Non-AC"

    public Train(int number, List<String> route, List<String> categories) {
        super(number, route);
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    // Polymorphism: Overriding displayInfo method
    @Override
    public void displayInfo() {
        System.out.println("Train Number: " + number + ", Path: " + String.join(" -> ", route) + ", Categories: " + categories);
    }

    @Override
    public String toString() {
        return "Train Number: " + number + ", Route: " + route + ", Categories: " + categories;
    }
}
