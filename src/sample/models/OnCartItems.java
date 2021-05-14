package sample.models;

public class OnCartItems extends Items{
    private int quantity;
    private int subTotal;
    public OnCartItems(Items selectedItem) {
        setName(selectedItem.getName());
        setPrice(selectedItem.getPrice());
        setImgSrc(selectedItem.getImgSrc());
        subTotal = 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        subTotal = getPrice() * quantity;
    }

    public int getSubTotal() {
        return subTotal;
    }

    @Override
    public String toString() {
        return "OnCartItems{" +
                super.toString() +
                "quantity=" + quantity +
                '}';
    }
}
