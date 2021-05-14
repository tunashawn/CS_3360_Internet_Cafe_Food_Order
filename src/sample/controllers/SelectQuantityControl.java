package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.main.Main;
import sample.models.Items;
import sample.models.OnCartItems;

import java.io.IOException;

public class SelectQuantityControl {

    private final Stage thisStage;
    private final MainFrameControl mainFrameControl;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label quantityLabel;
    @FXML
    private ImageView img;
    @FXML
    private JFXButton plusButton;
    @FXML
    private JFXButton minusButton;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton cancelButton;
    private int quantity = 1;
    private Items item;

    public SelectQuantityControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;

        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/SelectQuantityFrame.fxml"));

            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showStage() {
        mainFrameControl.showRegionPane();
        thisStage.initStyle(StageStyle.UNDECORATED);
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        plusButton.setOnAction(event -> increaseQuantity());
        minusButton.setOnAction(event -> decreaseQuantity());
        addButton.setOnAction(event -> addNewItemToCart());
        cancelButton.setOnAction(event -> closeThisStage());
    }

    // Set the initial data
    public void setData(Items item) {
        this.item = item;

        name.setText(item.getName());

        price.setText(Main.formatMoney(item.getPrice()));

        img.setImage(item.getImgSrc());

        quantityLabel.setText(String.valueOf(quantity));
    }


    private void increaseQuantity() {
        quantity++;
        quantityLabel.setText(String.valueOf(quantity));
    }


    private void decreaseQuantity() {
        if (quantity - 1 > 0) {
            quantity++;
            quantityLabel.setText(String.valueOf(quantity));
        }
    }


    private void addNewItemToCart() {
        OnCartItems newItem = new OnCartItems(item);
        newItem.setQuantity(quantity);
        // Add new item to shopping cart if it does not contain the selected item
        if (!mainFrameControl.isCartContains(newItem))
            mainFrameControl.addToOnCartItemList(newItem);
        closeThisStage();
    }

    private void closeThisStage() {
        mainFrameControl.hideRegionPane();
        thisStage.close();
    }

}
