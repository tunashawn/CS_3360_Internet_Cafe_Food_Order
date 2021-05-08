package sample.controllers;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.w3c.dom.events.MouseEvent;
import sample.main.MyListener;
import sample.models.Items;

import javafx.scene.image.ImageView ;
import sample.main.CafeInternetFoodOrderApp;

import java.util.Objects;

public class ItemControl {
    @FXML private Label nameLabel;

    @FXML private Label priceLabel;

    @FXML private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent){

    }

    private Items item;
    private MyListener myListener;

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Label getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(Label priceLabel) {
        this.priceLabel = priceLabel;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public MyListener getMyListener() {
        return myListener;
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    public void setData(Items item, MyListener myListener) {
        this.item = item;
        this.myListener = myListener;
        nameLabel.setText(item.getName());
        priceLabel.setText(CafeInternetFoodOrderApp.CURRENCY + item.getPrice());
        Image image = new Image(getClass().getResourceAsStream(item.getImgSrc()));
        img.setImage(image);
    }
}
