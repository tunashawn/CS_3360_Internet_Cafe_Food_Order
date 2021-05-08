package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import sample.models.Items;

public class OdersControl {
    @FXML private Label orderId;
    @FXML private Label time;
    @FXML private Label location;
    @FXML private Label totalPrice;
    @FXML private TableView<Items> itemsTableView;
}
