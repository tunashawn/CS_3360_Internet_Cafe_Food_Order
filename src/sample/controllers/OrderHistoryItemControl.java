package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.models.Orders;

import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class OrderHistoryItemControl {
    private final Stage thisStage;
    private final OrderHistoryControl orderHistoryControl;
    @FXML
    private Label numberLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label costLabel;
    private Orders order;
    @FXML
    private Button viewDetails;

    public OrderHistoryItemControl(OrderHistoryControl orderHistoryControl) {
        this.orderHistoryControl = orderHistoryControl;
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/OrderHistoryItem.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        viewDetails.setOnAction(event -> setViewDetailsLabel());
    }

    public void setData(Orders order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        this.order = order;
        numberLabel.setText(String.valueOf(order.getOrderNumber()));
        dateLabel.setText(formatter.format(order.getDateTime()));
        quantityLabel.setText(String.valueOf(order.getQuantity()));
        costLabel.setText(String.valueOf(order.getCost()));
    }

    private void setViewDetailsLabel() {
        orderHistoryControl.showRegionPane();
        System.out.println("view detail:\n" + order);
        OrderHistoryDetailControl orderHistoryDetailControl = new OrderHistoryDetailControl(orderHistoryControl);
        orderHistoryDetailControl.setData(order);
        orderHistoryDetailControl.showStage();
    }

}
