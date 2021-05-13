package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.w3c.dom.events.MouseEvent;
import sample.main.Main;
import sample.main.MyListener;
import sample.models.Items;

import java.io.IOException;
import java.text.DecimalFormat;

public class ItemControl {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView img;

    private Items item;
    private MyListener myListener;

    @FXML
    private void click(MouseEvent mouseEvent) {
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    @FXML
    private void click(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(item);
    }

    public void setData(Items item, MyListener myListener) {
        this.item = item;
        this.myListener = myListener;
        nameLabel.setText(item.getName());
        DecimalFormat df = new DecimalFormat("#,###");
        priceLabel.setText(df.format(item.getPrice()) + " " + Main.CURRENCY);

        img.setImage(item.getImgSrc());
    }
}
