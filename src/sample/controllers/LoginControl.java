package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DB.DAO;
import sample.DB.ItemsDAO;
import sample.models.Users;

import java.io.IOException;

public class LoginControl {
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordPF;
    @FXML
    private Button loginButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label warningLabel;


    private final Stage thisStage;


    public LoginControl() {

        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/LoginFrame.fxml"));

            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showStage() {
        thisStage.initStyle(StageStyle.UNDECORATED);
        thisStage.showAndWait();
    }

    @FXML
    private void initialize() {
        warningLabel.setVisible(false);
        // Set the action for the button that interact with MainFrame
        closeButton.setOnAction(event -> thisStage.close());
        loginButton.setOnAction(event -> setLoginButton());

        // Press enter while typing username or password will login
        usernameTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    setLoginButton();
                }
            }
        });
        passwordPF.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    setLoginButton();
                }
            }
        });

    }

    private void setLoginButton(){
        if (validateLogin()){
            MainFrameControl mainFrameControl = new MainFrameControl();
            // Show main windows
            mainFrameControl.showStage();

            Users user = ItemsDAO.getUserInfo(usernameTF.getText(), passwordPF.getText());
            mainFrameControl.setUserData(user);
            // Close login windows
            thisStage.close();
        }
        warningLabel.setVisible(true);
    }

    private boolean validateLogin(){
        return ItemsDAO.validateUser(usernameTF.getText(), passwordPF.getText());
    }


}
