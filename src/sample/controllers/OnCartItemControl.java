package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.main.Main;
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
    @FXML
    private Label orderTotalPrice;
    @FXML
    private JFXButton increaseQuantityButton;
    @FXML
    private JFXButton decreaseQuantityButton;
    @FXML
    private JFXButton deleteButton;
    private final MyOrderControl myOrderControl;
    private final Stage thisStage;
    private int quantity;
    private OnCartItems item;
    private int price;
    private int totalPrice;

    public OnCartItemControl(MyOrderControl myOrderControl) {
        this.myOrderControl = myOrderControl;

        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/OnCartItem.fxml"));

            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        // Set the action for the button that interact with MainFrame
        increaseQuantityButton.setOnAction(event -> setPlusButton());

        decreaseQuantityButton.setOnAction(event -> setMinusButton());

        deleteButton.setOnAction(event -> setDeleteButton());

    }


    /**
     * Increase quantity of selected item by 1
     */
    private void setPlusButton() {
        quantity++;
        System.out.println(quantity);
        quantityLabel.setText(String.valueOf(quantity));
        calculateTotal();
        myOrderControl.setQuantityForItem(item, quantity);
        myOrderControl.updateTotalPrice();
    }

    /**
     * Decrease quantity of the selected item by 1
     */
    private void setMinusButton() {
        if (quantity - 1 > 0) {
            quantity--;
            quantityLabel.setText(String.valueOf(quantity));
            calculateTotal();
            myOrderControl.setQuantityForItem(item, quantity);
            myOrderControl.updateTotalPrice();
        }
    }

    /**
     * Delete this item from Order List
     */
    private void setDeleteButton() {
        myOrderControl.deleteSelectedItemOnCart(this.item);
    }

    private void calculateTotal() {
        totalPrice = quantity * price;
        totalPriceLabel.setText(Main.formatMoney(totalPrice));
    }

    /**
     * Populate the scene with data from item
     *
     * @param item
     */
    public void setOnCartItemData(OnCartItems item) {
        this.item = item;
        // Set img
        imgLabel.setImage(item.getImgSrc());
        // Set price label
        price = item.getPrice();
        DecimalFormat df = new DecimalFormat("#,###");
        priceLabel.setText(df.format(price) + Main.CURRENCY);
        // Set name label
        nameLabel.setText(item.getName());
        // Set quantity label
        quantity = item.getQuantity();
        quantityLabel.setText(String.valueOf(item.getQuantity()));
        // Set total price
        totalPrice = price * quantity;
        totalPriceLabel.setText(df.format(totalPrice) + Main.CURRENCY);
    }
}
