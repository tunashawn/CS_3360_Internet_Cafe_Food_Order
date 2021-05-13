package sample.controllers;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.main.MyListener;
import sample.models.OnCartItems;

import java.io.IOException;

public class OrderHistoryControl {


    private final MainFrameControl mainFrameControl;
    private final Stage thisStage;
    private MyListener myListener;
    private OnCartItems item;

    public OrderHistoryControl(MainFrameControl mainFrameControl) {
        // We received the first controller, now let's make it usable throughout this controller.
        this.mainFrameControl = mainFrameControl;

        // Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/OrderHistoryControl.fxml"));
            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        // Set the action for the button that interact with MainFrame


    }
}
