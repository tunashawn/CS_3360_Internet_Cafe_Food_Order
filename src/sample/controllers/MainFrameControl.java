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
import sample.main.InternetCafeFoodOrderApp;
import sample.main.MyListener;
import sample.models.Items;
import sample.models.OnCartItems;
import sample.models.Orders;
import sample.models.Users;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFrameControl implements Initializable {
    private final Stage thisStage;
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



    private final List<Orders> ordersList = new ArrayList<>();
    private final List<Items> itemList = new ArrayList<>();
    private final List<OnCartItems> onCartItemsList = new ArrayList<>();
    private MyListener myListener;
    private int selectedQuantity = 1;
    private Items selectedItem = new Items();
    private OnCartItems selectedOnCartItem;

    private Users user;

    private  DecimalFormat df = new DecimalFormat("#,###");

    public MainFrameControl() {

        // TEST USER
        user = new Users();
        user.setUsername("shawnv1401");
        user.setBalance(100000);

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

    /**
     * Show the stage that was loaded in the constructor
     */
    public void showStage() {

        thisStage.initStyle(StageStyle.UNDECORATED);
        thisStage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(user.getUsername());
        balanceLabel.setText(df.format(user.getBalance()) + " " + "đ");

        selectQuantityPane.setVisible(false);
        myOrderPane.setVisible(false);
        numberOfItemOnCartLabel.setText("0");
    }

    public void deleteSelectedItemOnCart(OnCartItems item) {
        removeItemFromCart(item);
        refreshOrderTotalPrice();
        populateMyOrder();
    }

    public void setNumberOfItemOnCartLabel(){
        int numberOfAllItem = 0;
        if (onCartItemsList.size() > 0){
            for (OnCartItems i : onCartItemsList) {
                numberOfAllItem += i.getQuantity();
            }
        }
        numberOfItemOnCartLabel.setText(String.valueOf(numberOfAllItem));
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
        selectedTabName.setText("WcCafé® Drinks");
    }

    /**
     * ENERGY DRINK BUTTON
     *
     * @param event
     */
    @FXML
    private void setEnergyDrinkButton(ActionEvent event) {
        populateMenu("energy_drinks");
        selectedTabName.setText("Energy Drinks");
    }

    /**
     * BEVERAGES BUTTON
     *
     * @param event
     */
    @FXML
    private void setBeveragesButton(ActionEvent event) {
        populateMenu("beverages");
        selectedTabName.setText("Beverages");
    }

    /**
     * FASTFOOD BUTTON
     *
     * @param event
     */
    @FXML
    private void setFastFoodButton(ActionEvent event) {
        selectedTabName.setText("Fastfood");
    }

    /**
     * SAVOURY BUTTON
     *
     * @param event
     */
    @FXML
    private void setSavouryButton(ActionEvent event) {
        selectedTabName.setText("Savoury");
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
        if (isCartContains(newItem))
            System.out.println(selectedItem + " is already exist in your cart");
        else
            addItemToCart(newItem);

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
        myOrderPane.setVisible(true);
        populateMyOrder();
        refreshOrderTotalPrice();
        if (onCartItemsList.size() > 0){
            noOrderToDisplayPane.setVisible(false);
        } else {
            noOrderToDisplayPane.setVisible(true);
        }
    }


    /**
     * CLOSE BUTTON
     *
     * @param event
     */
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeMyOrderPaneButton(ActionEvent event) {
        myOrderPane.setVisible(false);
    }

    @FXML
    private void calculateTotalButton(ActionEvent event) {
        refreshOrderTotalPrice();
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
        this.selectedPrice.setText(df.format(item.getPrice()) + " " + InternetCafeFoodOrderApp.CURRENCY);
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

    /**
     * Populate My Order pane
     */
    public void populateMyOrder() {
        orderScroll.setVvalue(0.0);
        orderGrid.getChildren().clear();

        if (onCartItemsList.size() > 0) {
            myListener = new MyListener() {
                @Override
                public void onClickListener(Items item) throws IOException {
                    for (OnCartItems i : onCartItemsList) {
                        if (i.getName().equals(item.getName())) {
                            getSelectedOnCartItem(i);
                            break;
                        }
                    }
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (OnCartItems item : onCartItemsList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/OnCartItem.fxml"));

                OnCartItemControl onCartItemController = new OnCartItemControl(this);
                fxmlLoader.setController(onCartItemController);
                AnchorPane pane = fxmlLoader.load();
                onCartItemController.setOnCartItemData(item, myListener);

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                orderGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(5));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSelectedOnCartItem(OnCartItems item) {
        selectedOnCartItem = item;
    }

    public void refreshOrderTotalPrice() {
        double totalPrice = 0;
        for (OnCartItems onCartItems : onCartItemsList) {
            totalPrice += onCartItems.getPrice() * onCartItems.getQuantity();
        }
        orderTotalPrice.setText(df.format(totalPrice) + " " + InternetCafeFoodOrderApp.CURRENCY);
    }








    // Accessing on cart items methods

    private List<String> itemNameList = new ArrayList<>();

    public void addItemToCart(OnCartItems onCartItem) {
        onCartItemsList.add(onCartItem);
        itemNameList.add(onCartItem.getName());
    }

    public void removeItemFromCart(OnCartItems onCartItem) {
        onCartItemsList.remove(onCartItem);
        itemNameList.remove(onCartItem.getName());
    }

    public boolean isCartContains(OnCartItems onCartItem){
        return itemNameList.contains(onCartItem.getName());
    }


    public void setQuantityForItem(OnCartItems item, int quantity){
        int index = itemNameList.indexOf(item.getName());
        onCartItemsList.get(index).setQuantity(quantity);
    }




    @FXML
    private void setMyAccountButton(ActionEvent event){

    }
    @FXML
    private void setOrderHistoryButton(ActionEvent event){

    }
    @FXML
    private void setMakeOrderButton(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Purchase confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Confirm purchase?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Orders newOrder = new Orders(LocalDateTime.now(), "Hanoi", onCartItemsList, user.getUsername());
            System.out.println(newOrder);
        }

    }
}
