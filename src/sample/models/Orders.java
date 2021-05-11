package sample.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Orders {
    private LocalDateTime time;
    private String location;
    private List<OnCartItems> items;
    private String customerUsername;
    private double totalPrice = 0.0;

    public Orders( LocalDateTime time, String location, List<OnCartItems> items, String customerUsername) {
        this.time = time;
        this.location = location;
        this.items = items;
        this.customerUsername = customerUsername;
        for (Items item : items) {
            totalPrice += item.getPrice();
        }
    }



    public LocalDateTime getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public List<OnCartItems> getItems() {
        return items;
    }

    public void addItems(OnCartItems items) {
        this.items.add(items);
    }

    public String getCustomerId() {
        return customerUsername;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Orders{" +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", items=" + items +
                ", customerId=" + customerUsername +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
