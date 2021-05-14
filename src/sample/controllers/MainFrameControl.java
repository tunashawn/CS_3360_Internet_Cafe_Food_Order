package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DB.ItemsDAO;
import sample.main.Main;
import sample.main.MyListener;
import sample.models.Items;
import sample.models.OnCartItems;
import sample.models.Orders;
import sample.models.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class MainFrameControl implements Initializable {

    private final Stage thisStage;
    private final List<Orders> ordersList = new ArrayList<>();
    private final List<Items> itemList = new ArrayList<>();
    @FXML
    private Button closeButton;
    @FXML
    private Label numberOfItemOnCartLabel;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ImageView cartIcon;
    @FXML
    private Label selectedTabName;
    @FXML
    private GridPane orderGrid;
    @FXML
    private ScrollPane orderScroll;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Rectangle regionPane;
    @FXML
    private JFXButton coffeeButton, energyDrinkButton, beverageButton, banhmiButton, snackButton;
    @FXML
    private Button userInfoButton;

    private MyListener myListener;
    private int selectedQuantity = 1;
    private Items selectedItem = new Items();
    private OnCartItems selectedOnCartItem;
    private List<OnCartItems> onCartItemsList = new ArrayList<>();

    private Users user;


    private List<String> itemNameList = new ArrayList<>();

    public MainFrameControl() {

        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/MainFrame.fxml"));

            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        setCoffeeButton();
        hideRegionPane();
        numberOfItemOnCartLabel.setText("0");


        coffeeButton.setOnAction(event -> setCoffeeButton());
        energyDrinkButton.setOnAction(event -> setEnergyDrinkButton());
        beverageButton.setOnAction(event -> setBeverageButton());
        banhmiButton.setOnAction(event -> setBanhMiButton());
        snackButton.setOnAction(event -> setSnackButton());

        userInfoButton.setOnAction(event -> openUserInfoPane());
    }


    private void setCoffeeButton() {
        populateMenu("cafe");
        selectedTabName.setText("Coffee");
    }


    private void setEnergyDrinkButton() {
        populateMenu("energy_drinks");
        selectedTabName.setText("Energy Drink");
    }


    private void setBeverageButton() {
        populateMenu("beverages");
        selectedTabName.setText("Beverage");
    }


    private void setBanhMiButton() {
        populateMenu("banhmi");
        selectedTabName.setText("Banh Mi");
    }


    private void setSnackButton() {
        populateMenu("snack");
        selectedTabName.setText("Snack");
    }

    private void openUserInfoPane() {
        UserInfoControl userInfoControl = new UserInfoControl(this);
        userInfoControl.setData(user);
        userInfoControl.showStage();
    }

    /**
     * SHOPPING CART BUTTON
     *
     * @param event
     */
    @FXML
    private void setShoppingCartButton(ActionEvent event) {
        MyOrderControl myOrderControl = new MyOrderControl(this);

        myOrderControl.setData(onCartItemsList, itemNameList, this.user);

        myOrderControl.showStage();
    }

    /**
     * CLOSE BUTTON
     *
     * @param event
     */
    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        thisStage.close();
    }

    /**
     * LOG OUT BUTTON
     *
     * @param event
     */
    @FXML
    private void setLogOutButton(ActionEvent event) {
        thisStage.close();
        LoginControl loginControl = new LoginControl();
        loginControl.showStage();

    }

    /**
     * MY ACCOUNT BUTTON
     *
     * @param event
     */
    @FXML
    private void setMyAccountButton(ActionEvent event) {

    }

    /**
     * ORDER HISTORY BUTTON
     */
    @FXML
    private void setOrderHistoryButton(ActionEvent event) {

        ordersList.forEach(System.out::println);

        OrderHistoryControl orderHistoryControl = new OrderHistoryControl(this);
        orderHistoryControl.setData(ordersList);
        orderHistoryControl.showStage();

    }


    /**
     * Open selecting quantity for the selected item
     *
     * @param item
     */
    private void openSelectedItem(Items item) {
        SelectQuantityControl selectQuantityControl = new SelectQuantityControl(this);
        selectQuantityControl.setData(item);
        selectQuantityControl.showStage();
    }


    /**
     * Populate GridPane with items of type
     * Such as "cafe" or "fastfood"
     * Base on the database table name
     *
     * @param type
     */
    private void populateMenu(String type) {
        scroll.setVvalue(0.0);
        grid.getChildren().clear();
        itemList.clear();
        itemList.addAll(ItemsDAO.getItems(type));
        if (itemList.size() > 0) {
            myListener = new MyListener() {
                @Override
                public void onClickListener(Items item) throws IOException {
                    openSelectedItem(item);
                }

            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < itemList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemControl itemController = fxmlLoader.getController();
                itemController.setData(itemList.get(i), myListener);

                if (column == 4) {
                    column = 0;
                    ++row;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(19));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNumberOfItemOnCartLabel() {
        int numberOfAllItem = 0;
        if (onCartItemsList.size() > 0) {
            for (OnCartItems i : onCartItemsList) {
                numberOfAllItem += i.getQuantity();
            }
        }
        numberOfItemOnCartLabel.setText(String.valueOf(numberOfAllItem));
    }

    public boolean isCartContains(OnCartItems onCartItem) {
        return itemNameList.contains(onCartItem.getName());
    }

    public void showStage() {

        thisStage.initStyle(StageStyle.UNDECORATED);
        thisStage.show();
    }


    public void setUserData(Users user) {
        this.user = user;
        usernameLabel.setText(user.getUsername());
        balanceLabel.setText(Main.formatMoney(user.getBalance()));
    }


    public void updateOnCartItems(List<OnCartItems> onCartItemsList, List<String> itemNameList) {
        this.onCartItemsList = onCartItemsList;
        this.itemNameList = itemNameList;
        setNumberOfItemOnCartLabel();
    }

    public void addOrders(Orders newOrder) {
        ordersList.add(newOrder);
    }

    public List<Orders> getOrders() {
        return ordersList;
    }

    public void showRegionPane() {
        regionPane.setVisible(true);
    }

    public void hideRegionPane() {
        regionPane.setVisible(false);
    }

    public void addToOnCartItemList(OnCartItems newItem) {
        onCartItemsList.add(newItem);
        itemNameList.add(newItem.getName());
        setNumberOfItemOnCartLabel();
    }

    public void updateBalance(int amount) {
        user.setBalance(user.getBalance() - amount);
        balanceLabel.setText(Main.formatMoney(user.getBalance()));
    }
}
