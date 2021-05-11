package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;
import sample.main.InternetCafeFoodOrderApp;
import sample.main.MyListener;
import sample.models.OnCartItems;

import java.io.IOException;
import java.text.DecimalFormat;

public class OnCartItemControl {
    private final MainFrameControl mainFrameControl;
    private final Stage thisStage;
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
    private double price;
    private double totalPrice;
    private int quantity;
    private MyListener myListener;
    private OnCartItems item;


    public OnCartItemControl(MainFrameControl mainFrameControl) {
        // We received the first controller, now let's make it usable throughout this controller.
        this.mainFrameControl = mainFrameControl;

        // Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/OnCartItem.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
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
        mainFrameControl.setQuantityForItem(item, quantity);
        mainFrameControl.refreshOrderTotalPrice();
        mainFrameControl.setNumberOfItemOnCartLabel();
    }

    /**
     * Decrease quantity of the selected item by 1
     */
    private void setMinusButton() {
        if (quantity - 1 > 0) {
            quantity--;
            quantityLabel.setText(String.valueOf(quantity));
            calculateTotal();
            mainFrameControl.setQuantityForItem(item, quantity);
            mainFrameControl.refreshOrderTotalPrice();
            mainFrameControl.setNumberOfItemOnCartLabel();
        }
    }

    /**
     * Delete this item from Order List
     */
    private void setDeleteButton() {
        mainFrameControl.deleteSelectedItemOnCart(this.item);
    }


    @FXML
    private void click(MouseEvent mouseEvent) {

    }

    @FXML
    private void click(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(item);
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    private void calculateTotal() {
        totalPrice = quantity * price;
        DecimalFormat df = new DecimalFormat("#,###");
        totalPriceLabel.setText(df.format(totalPrice) + " " + InternetCafeFoodOrderApp.CURRENCY);
    }

    /**
     * Populate the scene with data from item
     *
     * @param item
     */
    public void setOnCartItemData(OnCartItems item, MyListener myListener) {
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
    }
}
