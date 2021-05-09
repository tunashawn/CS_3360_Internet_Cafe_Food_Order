package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.main.MyListener;
import sample.models.Items;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Mainframe implements Initializable {
    @FXML
    private Button closeButton;

    @FXML
    private JFXButton coffeeButton;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label selectedItem;

    @FXML
    private GridPane grid;

    private List<Items> coffee = new ArrayList<>();
    private MyListener myListener;
    private int quantity;


    @FXML
    void setCoffeeButton(ActionEvent event) throws IOException {
        coffee.addAll(getDummyData());
        if (coffee.size() > 0) {
            setChosenItem(coffee.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Items item) {
                    setChosenItem(item);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < coffee.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemControl itemController = fxmlLoader.getController();
                itemController.setData(coffee.get(i), myListener);

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


    @FXML
    void setJuiceButton(ActionEvent event){

    }


    @FXML
    void setFastFoodButton(ActionEvent event){

    }


    @FXML
    void setSavouryButton(ActionEvent event){

    }


    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }



    // Dummy Data
    private List<Items> getDummyData(){
        List<Items> coffee = new ArrayList<>();
        Items cohi;

        cohi = new Items();
        cohi.setName("Americano");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/americano.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Cappucino");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/cappucino.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Flat White");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/flatwhite.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Hot Choco");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/hotchoco.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Hot Latte");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/hotlatte.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Hot Matcha");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/hotmatcha.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Mocha");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/mocha.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Americano");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/americano.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Cappucino");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/cappucino.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Flat White");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/flatwhite.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Hot Choco");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/hotchoco.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Hot Latte");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/hotlatte.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Hot Matcha");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/hotmatcha.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Mocha");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/mocha.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Americano");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/americano.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Cappucino");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/cappucino.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Flat White");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/flatwhite.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Hot Choco");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/hotchoco.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Hot Latte");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/hotlatte.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("Hot Matcha");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/hotmatcha.png");
        coffee.add(cohi);

        cohi = new Items();
        cohi.setName("hahaha");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/mocha.png");
        coffee.add(cohi);

        return coffee;
    }

    private void setChosenItem(Items item) {
        selectedItem.setText(item.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
