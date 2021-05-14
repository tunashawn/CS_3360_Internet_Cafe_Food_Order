package sample.models;

import javafx.scene.image.Image;

public class Items {
    private String name;
    private int price;
    private Image imgSrc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Image getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(Image imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "Items{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
