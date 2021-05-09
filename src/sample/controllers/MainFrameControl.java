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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.DB.ItemsDAO;
import sample.main.CafeInternetFoodOrderApp;
import sample.main.MyListener;
import sample.models.Items;
import sample.models.OnCartItems;
import sample.models.Orders;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
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

    private List<Orders> ordersList = new ArrayList<>();
    private List<Items> itemList = new ArrayList<>();
    private List<OnCartItems> onCartItemsList = new ArrayList<>();
    private MyListener myListener;
    private int selectedQuantity = 1;
    private Items selectedItem = new Items();

    @FXML
    private void setCoffeeButton(ActionEvent event) throws IOException {
        populateMenu("cafe");
        selectedTabName.setText("WcCafé® Drinks");
    }

    @FXML
    private void setEnergyDrinkButton(ActionEvent event){
        selectedTabName.setText("Energy Drinks");
    }

    @FXML
    private void setBeveragesButton(ActionEvent event){
        populateMenu("beverages");
        selectedTabName.setText("Beverages");
    }

    @FXML
    private void setFastFoodButton(ActionEvent event){
        selectedTabName.setText("Fastfood");
    }

    @FXML
    private void setSavouryButton(ActionEvent event){
        selectedTabName.setText("Savoury");
    }

    @FXML
    private void setPlusButton(ActionEvent event){
        selectedQuantity ++;
        System.out.println(selectedQuantity);
        quantityTextField.setText(String.valueOf(selectedQuantity));
    }

    @FXML
    private void setMinusButton(ActionEvent event){
        if (selectedQuantity - 1 > 0){
            selectedQuantity --;
            quantityTextField.setText(String.valueOf(selectedQuantity));
        }
    }

    @FXML
    private void setAddButton(ActionEvent event){
        // Create new item to put on cart
        OnCartItems newItem = new OnCartItems(selectedItem);
        // Set the quantity of new item
        newItem.setQuantity(selectedQuantity);
        // Add new item to list of on cart items
        onCartItemsList.add(newItem);
        // Then hide the selecting quantity panel
        selectQuantityPane.setVisible(false);

        System.out.println(newItem);
    }

    @FXML
    private void setCancelButton(ActionEvent event){
        selectQuantityPane.setVisible(false);
    }


    @FXML
    private void setShoppingCartButton(ActionEvent event){

    }



    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectQuantityPane.setVisible(false);

    }

    /**
     * Populate menu with items
     * @param item
     */
    private void populateSelectQuantityPane(Items item){
        this.selectedName.setText(item.getName());
        DecimalFormat df = new DecimalFormat("#,###");
        this.selectedPrice.setText(df.format(item.getPrice()) + " " + CafeInternetFoodOrderApp.CURRENCY);
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

}
