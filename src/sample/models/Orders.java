package sample.models;

import java.time.LocalDateTime;
import java.util.List;

public class Orders {
    private final int orderNumber;
    private final String username;
    private final LocalDateTime dateTime;
    private final List<OnCartItems> itemsList;
    private final int quantity;
    private final int cost;

    public Orders(int orderNumber, String username, LocalDateTime dateTime, List<OnCartItems> itemsList, int quantity, int cost) {
        this.orderNumber = orderNumber;
        this.username = username;
        this.dateTime = dateTime;
        this.itemsList = itemsList;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<OnCartItems> getItemsList() {
        return itemsList;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCost() {
        return cost;
    }
}
