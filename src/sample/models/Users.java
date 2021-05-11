package sample.models;

import javafx.scene.image.Image;

public class Users {
    private String username;
//    private Image avatar;
    private double balance;

    public Users(String username, double balance) {
        this.username = username;
//        this.avatar = avatar;
        this.balance = balance;
    }

    public Users() {
        this.username = "";
//        this.avatar = avatar;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public Image getAvatar() {
//        return avatar;
//    }

//    public void setAvatar(Image avatar) {
//        this.avatar = avatar;
//    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
