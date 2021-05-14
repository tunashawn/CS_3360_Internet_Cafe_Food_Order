package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.models.Orders;

import java.io.IOException;
import java.util.List;

public class OrderHistoryControl {

    private final Stage thisStage;
    private final MainFrameControl mainFrameControl;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private Button closeButton;
    @FXML
    private Rectangle regionPane;
    @FXML
    private Pane noOrderPane;

    public OrderHistoryControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/OrderHistoryFrame.fxml"));
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
        noOrderPane.setVisible(false);
        hideRegionPane();
        // Set the action for the button that interact with MainFrame
        closeButton.setOnAction(event -> {
            mainFrameControl.hideRegionPane();
            thisStage.close();
        });
    }

    public void setData(List<Orders> ordersList) {
        if (ordersList != null || ordersList.size() > 0) {
            populateMyOrder(ordersList);
        }
    }

    private void populateMyOrder(List<Orders> ordersList) {
        if (ordersList.size() > 0) {
            scroll.setVvalue(0.0);
            grid.getChildren().clear();

            int column = 0;
            int row = 1;
            try {
                for (Orders order : ordersList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/views/OrderHistoryItem.fxml"));

                    OrderHistoryItemControl orderHistoryItemControl = new OrderHistoryItemControl(this);
                    fxmlLoader.setController(orderHistoryItemControl);
                    AnchorPane pane = fxmlLoader.load();
                    orderHistoryItemControl.setData(order);

                    if (column == 1) {
                        column = 0;
                        ++row;
                    }
                    grid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(5));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            noOrderPane.setVisible(true);
        }
    }


    public void showRegionPane() {
        regionPane.setVisible(true);
    }

    public void hideRegionPane() {
        regionPane.setVisible(false);
    }
}
