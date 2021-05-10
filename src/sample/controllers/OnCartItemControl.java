package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;
import sample.data.Data;
import sample.main.InternetCafeFoodOrderApp;
import sample.main.MyListener;
import sample.models.OnCartItems;

import java.io.IOException;
import java.text.DecimalFormat;

public class OnCartItemControl {
    @FXML
    private ImageView imgLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label totalPriceLabel;

    private int itemIndex;
    private double price;
    private double totalPrice;
    private int quantity;
    private MyListener myListener;
    private OnCartItems item;

    @FXML
    private void click(MouseEvent mouseEvent){

    }
    @FXML
    private void click(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(item);
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    /**
     * PLUS BUTTON
     * Increase quantity of selected item by 1
     * @param event
     */
    @FXML
    private void setPlusButton(ActionEvent event){
        quantity ++;
        System.out.println(quantity);
        quantityLabel.setText(String.valueOf(quantity));
        calculateTotal();
        Data.getOnCartItemList().get(itemIndex).setQuantity(quantity);
    }

    /**
     * MINUS BUTTON
     * Decrease quantity of the selected item by 1
     * @param event
     */
    @FXML
    private <MyAppController> void setMinusButton(ActionEvent event){
        if (quantity - 1 > 0){
            quantity --;
            quantityLabel.setText(String.valueOf(quantity));
            calculateTotal();
            Data.getOnCartItemList().get(itemIndex).setQuantity(quantity);

        }
    }

    @FXML
    private void setDeleteButton(ActionEvent event){

    }

    private void calculateTotal(){
        totalPrice = quantity * price;
        DecimalFormat df = new DecimalFormat("#,###");
        totalPriceLabel.setText(df.format(totalPrice) + " " + InternetCafeFoodOrderApp.CURRENCY);
    }

    /**
     * Populate the scene with data from item
     * @param item
     */
    public void setOnCartItemData(OnCartItems item, MyListener myListener){
        this.myListener = myListener;
        this.item = item;
        // Set img
        imgLabel.setImage(item.getImgSrc());
        // Set price label
        price = item.getPrice();
        DecimalFormat df = new DecimalFormat("#,###");
        priceLabel.setText(df.format(price) + InternetCafeFoodOrderApp.CURRENCY);
        // Set name label
        nameLabel.setText(item.getName());
        // Set quantity label
        quantity = item.getQuantity();
        quantityLabel.setText(String.valueOf(item.getQuantity()));
        // Set total price
        totalPrice = price * quantity;
        totalPriceLabel.setText(df.format(totalPrice) + InternetCafeFoodOrderApp.CURRENCY);

        itemIndex = Data.getOnCartItemList().indexOf(item);
    }
}
