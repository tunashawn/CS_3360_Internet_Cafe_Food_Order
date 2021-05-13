package sample.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.controllers.LoginControl;
import sample.controllers.MainFrameControl;

public class InternetCafeFoodOrderApp extends Application {
    public static final String CURRENCY = "đ";

    @Override
    public void start(Stage primaryStage) throws Exception{

        LoginControl loginControl = new LoginControl();
        loginControl.showStage();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
