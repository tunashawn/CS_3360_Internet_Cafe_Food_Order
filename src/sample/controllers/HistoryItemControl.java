package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.models.Orders;

import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class HistoryItemControl {
    private final Stage thisStage;
    @FXML
    private Label numberLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Label viewDetailsLabel;
    private Orders order;

    public HistoryItemControl() {

        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/HistoryItem.fxml"));

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

    public void setData(Orders order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        this.order = order;
        numberLabel.setText(String.valueOf(order.getOrderNumber()));
        dateLabel.setText(formatter.format(order.getDateTime()));
        quantityLabel.setText(String.valueOf(order.getQuantity()));
        costLabel.setText(String.valueOf(order.getCost()));
    }


}
