package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Mainframe {
    @FXML private Button closeButton;
    @FXML private JFXButton coffeeButton;
    @FXML private BorderPane mainBorderPane;


    @FXML
    void setCoffeeButton(ActionEvent event) throws IOException {
        loadUI("/sample/views/FoodAndBeverageFrame");
    }

    @FXML
    void onStudentsButtonClicked(ActionEvent event) throws IOException {
        loadUI("/sample/views/FoodAndBeverageFrame");
    }


    @FXML
    void onParentsButtonClicked(ActionEvent event) throws IOException {
        loadUI("/sample/views/FoodAndBeverageFrame");
    }

    @FXML
    void onTeachersButtonClicked(ActionEvent event) throws IOException {
        loadUI("/sample/views/FoodAndBeverageFrame");
    }


    private void loadUI(String layout) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(layout + ".fxml"));
        mainBorderPane.setCenter(root);
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
