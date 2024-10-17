package entities;

public class Tour {
    private long id;
    private String title;
    private String description;
    private String location;
    private double price;
    private String[] availableSpots;

    public Tour(long id, String title, String description, String location, double price, String[] availableSpots) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
        this.availableSpots = availableSpots;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public String[] getAvailableSpots() {
        return availableSpots;
    }

    @Override
    public String toString() {
        return "Tour {" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", availableSpots=" + String.join(", ", availableSpots) +
                '}';
    }
}
