package sample.models;

public class OnCartItems extends Items{
    private int quantity;

    public OnCartItems(Items selectedItem) {
        setName(selectedItem.getName());
        setPrice(selectedItem.getPrice());
        setImgSrc(selectedItem.getImgSrc());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OnCartItems{" +
                super.toString() +
                "quantity=" + quantity +
                '}';
    }
}
