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
//    private double xOffset = 0;
//    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{

        LoginControl loginControl = new LoginControl();
        loginControl.showStage();

//        MainFrameControl mainFrameControl = new MainFrameControl();
//
//        mainFrameControl.showStage();


//        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/MainFrame.fxml"));
//
//        root.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                xOffset = event.getSceneX();
//                yOffset = event.getSceneY();
//            }
//        });
//
//        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                primaryStage.setX(event.getScreenX() - xOffset);
//                primaryStage.setY(event.getScreenY() - yOffset);
//            }
//        });
//
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//
//        primaryStage.setTitle("Food Order");
//
//        primaryStage.setScene(new Scene(root));
//
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
