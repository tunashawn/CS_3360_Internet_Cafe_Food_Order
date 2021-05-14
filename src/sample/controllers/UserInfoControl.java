package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DB.ItemsDAO;
import sample.main.Main;
import sample.models.Users;

import java.io.IOException;

public class UserInfoControl {
    private final MainFrameControl mainFrameControl;
    private final Stage thisStage;
    @FXML
    private Label usernameLabel, phoneNumberLabel, emailLabel, balanceLabel;
    @FXML
    private JFXButton changePasswordButton;
    @FXML
    private Button closeButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label warningLabel, succeedLabel;
    private Users user;

    public UserInfoControl(MainFrameControl mainFrameControl) {
        this.mainFrameControl = mainFrameControl;

        thisStage = new Stage();

        //Load FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/views/UserInfoFrame.fxml"));

            loader.setController(this);

            thisStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        warningLabel.setVisible(false);
        succeedLabel.setVisible(false);
        closeButton.setOnAction(event -> {
            mainFrameControl.hideRegionPane();
            thisStage.close();
        });
        changePasswordButton.setOnAction(event -> changePassword());
    }

    public void showStage() {
        mainFrameControl.showRegionPane();
        thisStage.initStyle(StageStyle.UNDECORATED);
        thisStage.showAndWait();
    }

    public void setData(Users user) {
        this.user = user;
        usernameLabel.setText(user.getUsername());
        phoneNumberLabel.setText(user.getPhoneNumber());
        emailLabel.setText(user.getEmail());
        balanceLabel.setText(String.valueOf(user.getBalance()));
    }

    public void changePassword() {
        if (passwordField.getText().length() >= Main.passwordLength) {
            String newPassword = passwordField.getText();
            user.setPassword(newPassword);
            ItemsDAO.updateUserInfo(user);
            succeedLabel.setVisible(true);
            warningLabel.setVisible(false);
        } else {
            succeedLabel.setVisible(false);
            warningLabel.setVisible(true);
        }
    }
}
