package sample.models;

import java.util.Date;
import java.util.List;

public class Orders {
    private int orderId;
    private Date time;
    private String location;
    private List<Items> items;
    private int customerId;
    private double totalPrice;

    public Orders(int orderId, Date time, String location, List<Items> items, int customerId) {
        this.orderId = orderId;
        this.time = time;
        this.location = location;
        this.items = items;
        this.customerId = customerId;
        totalPrice = 0.0;
        for (Items item : items) {
            totalPrice += item.getPrice();
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public List<Items> getItems() {
        return items;
    }

    public void addItems(Items items) {
        this.items.add(items);
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", items=" + items +
                ", customerId=" + customerId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
