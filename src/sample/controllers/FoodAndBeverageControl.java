package sample.controllers;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import sample.main.MyListener;
import sample.models.Items;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FoodAndBeverageControl implements Initializable {
    @FXML private AnchorPane coffeePane;
    @FXML private ScrollPane scroll;
    @FXML private GridPane grid;

    private List<Items> coffee = new ArrayList<>();
    private MyListener myListener;


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
        cohi.setName("Mocha");
        cohi.setPrice(1.23);
        cohi.setImgSrc("/sample/img/Coffee/mocha.png");
        coffee.add(cohi);

        return coffee;
    }

    private void setChosenFruit(Items item) {
        System.out.println(item.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add all items to item list
        coffee.addAll(getDummyData());
        if (coffee.size() > 0) {
            setChosenFruit(coffee.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Items item) {
                    setChosenFruit(item);
                    System.out.println("CLICK ON ITEM: " + item.toString());
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
}
