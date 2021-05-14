package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DB.ItemsDAO;
import sample.main.Main;
import sample.models.OnCartItems;
import sample.models.Orders;
import sample.models.Users;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyOrderControl {

    private final Stage thisStage;
    private final MainFrameControl mainFrameControl;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private VBox noOrderPane;
    @FXML
    private JFXButton makeOrderButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label totalPriceLabel;


    private List<OnCartItems> onCartItemsList;
    private List<String> itemNameList = new ArrayList<>();
    private Users user;

    public MyOrderControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;

        thisStage = new Stage();

        //Load FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/MyOrderFrame.fxml"));

            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        mainFrameControl.showRegionPane();
        thisStage.initStyle(StageStyle.UNDECORATED);
        thisStage.show();
    }

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> {
            mainFrameControl.hideRegionPane();
            closeStage();
        });

        makeOrderButton.setOnAction(event -> makeOrder());

    }


    private void closeStage() {
        thisStage.close();
        if (onCartItemsList != null || onCartItemsList.size() > 0) {
            mainFrameControl.updateOnCartItems(onCartItemsList, itemNameList);
        }
    }

    public void deleteSelectedItemOnCart(OnCartItems item) {
        removeItemFromCart(item);
        updateTotalPrice();
        populateMyOrder();
    }

    public void setData(List<OnCartItems> onCartItemsList, List<String> itemNameList, Users user) {
        this.onCartItemsList = new ArrayList<>();
        if (onCartItemsList.size() > 0) {
            this.onCartItemsList = onCartItemsList;
            this.itemNameList = itemNameList;
            this.user = user;

            updateTotalPrice();
            populateMyOrder();
            noOrderPane.setVisible(false);
        } else {
            noOrderPane.setVisible(true);
            totalPriceLabel.setText("0 đ");
        }
    }


    private void makeOrder() {
        int totalCost = 0;
        for (OnCartItems i : onCartItemsList) {
            totalCost += i.getQuantity() * i.getPrice();
        }
        if (mainFrameControl.getUserBalance() > totalCost) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Purchase confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Confirm purchase?");
            Optional<ButtonType> result = alert.showAndWait();

            int quantity = 0;
            for (OnCartItems i : onCartItemsList) {
                quantity += i.getQuantity();
            }
            if (result.get() == ButtonType.OK) {
                Orders newOrder = new Orders(
                        1,
                        this.user.getUsername(),
                        LocalDateTime.now(),
                        onCartItemsList,
                        quantity,
                        totalCost
                );
                // Add new order to order list
                mainFrameControl.addOrders(newOrder);
                // Clear on shopping cart items list
                onCartItemsList.clear();
                itemNameList.clear();

                mainFrameControl.updateBalance(totalCost);
                mainFrameControl.hideRegionPane();
                // Update User Info on database
                ItemsDAO.updateUserInfo(user);

                closeStage();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("PURCHASE ERROR!");
            alert.setContentText("Your balance is not enough to purchase items!");
            alert.show();
        }
    }


    public void updateTotalPrice() {
        int totalPrice = 0;
        for (OnCartItems onCartItems : onCartItemsList) {
            totalPrice += onCartItems.getPrice() * onCartItems.getQuantity();
        }
        totalPriceLabel.setText(Main.formatMoney(totalPrice));
    }

    public void setQuantityForItem(OnCartItems item, int quantity) {
        int index = itemNameList.indexOf(item.getName());
        onCartItemsList.get(index).setQuantity(quantity);
    }

    public void removeItemFromCart(OnCartItems onCartItem) {
        // Remove from list of items on cart
        onCartItemsList.remove(onCartItem);
        // remove from list of name of items on cart
        itemNameList.remove(onCartItem.getName());
    }


    /**
     * Populate My Order pane
     */
    public void populateMyOrder() {
        if (onCartItemsList.size() > 0) {
            scroll.setVvalue(0.0);
            grid.getChildren().clear();

            int column = 0;
            int row = 1;
            try {
                for (OnCartItems item : onCartItemsList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/sample/views/OnCartItem.fxml"));

                    OnCartItemControl onCartItemController = new OnCartItemControl(this);
                    fxmlLoader.setController(onCartItemController);
                    AnchorPane pane = fxmlLoader.load();
                    onCartItemController.setOnCartItemData(item);

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
            totalPriceLabel.setText("0 đ");
        }
    }


}
