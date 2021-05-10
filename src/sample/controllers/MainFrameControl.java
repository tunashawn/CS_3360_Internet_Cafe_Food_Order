package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.DB.ItemsDAO;
import sample.data.Data;
import sample.main.InternetCafeFoodOrderApp;
import sample.main.MyListener;
import sample.models.Items;
import sample.models.OnCartItems;
import sample.models.Orders;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFrameControl implements Initializable {
    @FXML
    private Button closeButton;

    @FXML
    private JFXButton coffeeButton;

    @FXML
    private BorderPane mainBorderPane;


    @FXML
    private Label numberOfItemLabel;

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



    private List<Orders> ordersList = new ArrayList<>();
    private List<Items> itemList = new ArrayList<>();
    private List<OnCartItems> onCartItemsList = new ArrayList<>();
    private MyListener myListener;
    private int selectedQuantity = 1;
    private Items selectedItem = new Items();
    private OnCartItems selectedOnCartItem;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectQuantityPane.setVisible(false);
        myOrderPane.setVisible(false);
    }

    /**
     * COFFEE BUTTON
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
     * @param event
     */
    @FXML
    private void setEnergyDrinkButton(ActionEvent event){
        selectedTabName.setText("Energy Drinks");
    }

    /**
     * BEVERAGES BUTTON
     * @param event
     */
    @FXML
    private void setBeveragesButton(ActionEvent event){
        populateMenu("beverages");
        selectedTabName.setText("Beverages");
    }

    /**
     * FASTFOOD BUTTON
     * @param event
     */
    @FXML
    private void setFastFoodButton(ActionEvent event){
        selectedTabName.setText("Fastfood");
    }

    /**
     * SAVOURY BUTTON
     * @param event
     */
    @FXML
    private void setSavouryButton(ActionEvent event){
        selectedTabName.setText("Savoury");
    }

    /**
     * PLUS BUTTON
     * Increase quantity of selected item by 1
     * @param event
     */
    @FXML
    private void setPlusButton(ActionEvent event){
        selectedQuantity ++;
        System.out.println(selectedQuantity);
        quantityTextField.setText(String.valueOf(selectedQuantity));
    }

    /**
     * MINUS BUTTON
     * Decrease quantity of the selected item by 1
     * @param event
     */
    @FXML
    private void setMinusButton(ActionEvent event){
        if (selectedQuantity - 1 > 0){
            selectedQuantity --;
            quantityTextField.setText(String.valueOf(selectedQuantity));
        }
    }

    /**
     * ADD BUTTON
     * Add selected item to the shopping cart
     * @param event
     */
    @FXML
    private void setAddButton(ActionEvent event){
        // Create new item to put on cart
        OnCartItems newItem = new OnCartItems(selectedItem);
        // Set the quantity of new item
        newItem.setQuantity(selectedQuantity);
        // Add new item to list of items on shopping cart if it is not in the list yet
        if (Data.isContain(newItem))
            System.out.println(selectedItem + " is already exist in your cart");
        else
            Data.addItem(newItem);

        // Then hide the selecting quantity panel
        selectQuantityPane.setVisible(false);
        Data.getOnCartItemList().forEach(System.out::println);
    }

    /**
     * CANCEL BUTTON
     * Hide the selecting quantity panel
     * @param event
     */
    @FXML
    private void setCancelButton(ActionEvent event){
        selectQuantityPane.setVisible(false);
        selectedQuantity = 1;
    }

    /**
     * SHOPPING CART BUTTON
     * @param event
     */
    @FXML
    private void setShoppingCartButton(ActionEvent event){
        myOrderPane.setVisible(true);
        populateOnCartItemList();
        refreshOrderTotalPrice();
    }


    /**
     * CLOSE BUTTON
     * @param event
     */
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeMyOrderPaneButton(ActionEvent event){
        myOrderPane.setVisible(false);
    }

    @FXML
    private void calculateTotalButton(ActionEvent event){
        refreshOrderTotalPrice();
    }

    @FXML
    private void deleteSelectedItemButton(ActionEvent event){
        if (selectedOnCartItem.getName().length() > 0){
            Data.removeItem(selectedOnCartItem);
            refreshOrderTotalPrice();
            populateOnCartItemList();
        }
    }


    /**
     * Set the choosen item on menu
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
     * @param item
     */
    private void populateSelectQuantityPane(Items item){
        this.selectedName.setText(item.getName());
        DecimalFormat df = new DecimalFormat("#,###");
        this.selectedPrice.setText(df.format(item.getPrice()) + " " + InternetCafeFoodOrderApp.CURRENCY);
        this.selectedImg.setImage(item.getImgSrc());
        this.quantityTextField.setText("1");
        this.selectedQuantity = 1;
    }

    /**
     * Populate GridPane with items of type
     * Such as "cafe" or "fastfood"
     * Base on the database table name
     * @param type
     */
    private void populateMenu(String type){
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

                grid.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(13));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateOnCartItemList(){
        orderScroll.setVvalue(0.0);
        orderGrid.getChildren().clear();
        onCartItemsList.clear();
        onCartItemsList.addAll(Data.getOnCartItemList());
        if (onCartItemsList.size() > 0) {
            myListener = new MyListener() {
                @Override
                public void onClickListener(Items item) throws IOException {
                    for (int i = 0; i < Data.getOnCartItemList().size(); i++) {
                        if (Data.getOnCartItemList().get(i).getName().equals(item.getName())){
                            getSelectedOnCartItem(Data.getOnCartItemList().get(i));
                            System.out.println(Data.getOnCartItemList().get(i));
                            break;
                        }
                    }
                }

            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < onCartItemsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/OnCartItem.fxml"));
                AnchorPane pane = fxmlLoader.load();

                OnCartItemControl onCartItemController = fxmlLoader.getController();
                onCartItemController.setOnCartItemData(onCartItemsList.get(i), myListener);

                if (column == 1) {
                    column = 0;
                    ++row;
                }

                orderGrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSelectedOnCartItem(OnCartItems item){
        selectedOnCartItem = item;
    }

    public void refreshOrderTotalPrice(){
        double totalPrice = 0;
        for (OnCartItems onCartItems : Data.getOnCartItemList()) {
            totalPrice += onCartItems.getPrice() * onCartItems.getQuantity();
        }
        DecimalFormat df = new DecimalFormat("#,###");
        orderTotalPrice.setText(df.format(totalPrice) + " " + InternetCafeFoodOrderApp.CURRENCY);
    }
}
