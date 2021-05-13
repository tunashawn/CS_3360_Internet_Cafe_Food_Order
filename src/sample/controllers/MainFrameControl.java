package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

    @FXML
    private Button closeButton;
    @FXML
    private JFXButton coffeeButton;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Label numberOfItemOnCartLabel;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Pane selectQuantityPane;
    @FXML
    private Label selectedName;
    @FXML
    private Label selectedPrice;
    @FXML
    private ImageView selectedImg;
    @FXML
    private TextField quantityTextField;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private ImageView cartIcon;
    @FXML
    private Label selectedTabName;
    @FXML
    private GridPane orderGrid;
    @FXML
    private ScrollPane orderScroll;
    @FXML
    private Pane myOrderPane;
    @FXML
    private Label orderTotalPrice;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private VBox noOrderToDisplayPane;
    private MyListener myListener;
    private int selectedQuantity = 1;
    private Items selectedItem = new Items();
    private OnCartItems selectedOnCartItem;

    private final Stage thisStage;
    private final List<Orders> ordersList = new ArrayList<>();
    private final List<Items> itemList = new ArrayList<>();
    private List<OnCartItems> onCartItemsList = new ArrayList<>();

    private Users user;


    private List<String> itemNameList = new ArrayList<>();

    public MainFrameControl() {

        // Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/MainFrame.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

        selectQuantityPane.setVisible(false);
        numberOfItemOnCartLabel.setText("0");
    }


    /**
     * COFFEE BUTTON
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void setCoffeeButton(ActionEvent event) throws IOException {
        populateMenu("cafe");
        selectedTabName.setText("Coffee");
    }

    /**
     * ENERGY DRINK BUTTON
     *
     * @param event
     */
    @FXML
    private void setEnergyDrinkButton(ActionEvent event) {
        populateMenu("energy_drinks");
        selectedTabName.setText("Energy Drink");
    }

    /**
     * BEVERAGES BUTTON
     *
     * @param event
     */
    @FXML
    private void setBeverageButton(ActionEvent event) {
        populateMenu("beverages");
        selectedTabName.setText("Beverage");
    }

    /**
     * BANH MI BUTTON
     *
     * @param event
     */
    @FXML
    private void setBanhMiButton(ActionEvent event) {
        populateMenu("banhmi");
        selectedTabName.setText("Banh Mi");
    }

    /**
     * SAVOURY BUTTON
     *
     * @param event
     */
    @FXML
    private void setSnackButton(ActionEvent event) {
        populateMenu("snack");
        selectedTabName.setText("Snack");
    }

    /**
     * PLUS BUTTON
     * Increase quantity of selected item by 1
     *
     * @param event
     */
    @FXML
    private void setPlusButton(ActionEvent event) {
        selectedQuantity++;
        System.out.println(selectedQuantity);
        quantityTextField.setText(String.valueOf(selectedQuantity));
    }

    /**
     * MINUS BUTTON
     * Decrease quantity of the selected item by 1
     *
     * @param event
     */
    @FXML
    private void setMinusButton(ActionEvent event) {
        if (selectedQuantity - 1 > 0) {
            selectedQuantity--;
            quantityTextField.setText(String.valueOf(selectedQuantity));
        }
    }

    /**
     * ADD BUTTON
     * Add selected item to the shopping cart
     *
     * @param event
     */
    @FXML
    private void setAddButton(ActionEvent event) {
        // Create new item to put on cart
        OnCartItems newItem = new OnCartItems(selectedItem);
        // Set the quantity of new item
        newItem.setQuantity(selectedQuantity);
        // Add new item to list of items on shopping cart if it is not in the list yet
        if (isCartContains(newItem)) {
            System.out.println(selectedItem + " is already exist in your cart");
        } else {
            onCartItemsList.add(newItem);
            itemNameList.add(newItem.getName());
        }

        // Then hide the selecting quantity panel
        selectQuantityPane.setVisible(false);
        setNumberOfItemOnCartLabel();
    }

    /**
     * CANCEL BUTTON
     * Hide the selecting quantity panel
     *
     * @param event
     */
    @FXML
    private void setCancelButton(ActionEvent event) {
        selectQuantityPane.setVisible(false);
        selectedQuantity = 1;
    }

    /**
     * SHOPPING CART BUTTON
     *
     * @param event
     */
    @FXML
    private void setShoppingCartButton(ActionEvent event) {
        MyOrderControl myOrderControl = new MyOrderControl(this);

        myOrderControl.setData(onCartItemsList, itemNameList, user.getUsername());

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
        OrderHistoryControl orderHistoryControl = new OrderHistoryControl();
        orderHistoryControl.setData(ordersList);
        orderHistoryControl.showStage();

    }


    /**
     * Set the choosen item on menu
     *
     * @param item
     */
    private void setChosenItem(Items item) {
        selectQuantityPane.setVisible(true);
        populateSelectQuantityPane(item);
        // Set the choosen item
        selectedItem = item;
    }

    /**
     * Populate menu with items
     *
     * @param item
     */
    private void populateSelectQuantityPane(Items item) {
        this.selectedName.setText(item.getName());
        this.selectedPrice.setText(Main.df.format(item.getPrice()) + " " + Main.CURRENCY);
        this.selectedImg.setImage(item.getImgSrc());
        this.quantityTextField.setText("1");
        this.selectedQuantity = 1;
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
                    setChosenItem(item);
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

    private boolean isCartContains(OnCartItems onCartItem) {
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

}
