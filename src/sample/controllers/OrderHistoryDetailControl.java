package sample.controllers;

import com.mysql.cj.log.Log;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.main.Main;
import sample.models.OnCartItems;
import sample.models.Orders;

import java.io.IOException;

public class OrderHistoryDetailControl {
    private final Stage thisStage;
    private final OrderHistoryControl orderHistoryControl;
    @FXML
    private Button closeButton;
    @FXML
    private TableColumn<OnCartItems, String> itemCol;
    @FXML
    private TableColumn<OnCartItems, String> priceCol;
    @FXML
    private TableColumn<OnCartItems, String> quantityCol;
    @FXML
    private TableColumn<Log, Integer> totalCol;
    @FXML
    private TableView<OnCartItems> table;
    @FXML
    private Label orderNumberLabel, totalCostLabel;


    public OrderHistoryDetailControl(OrderHistoryControl orderHistoryControl) {
        this.orderHistoryControl = orderHistoryControl;
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/OrderHistoryDetailFrame.fxml"));

            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showStage() {
        thisStage.initStyle(StageStyle.UNDECORATED);
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> {
            orderHistoryControl.hideRegionPane();
            thisStage.close();
        });
    }

    public void setData(Orders order) {
        orderNumberLabel.setText("Order # " + order.getOrderNumber());
        totalCostLabel.setText(Main.formatMoney(order.getCost()));
        for (OnCartItems item : order.getItemsList()) {
            System.out.println(item);
            table.getItems().add(item);
            itemCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            totalCol.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
            table.refresh();
        }
    }
}
