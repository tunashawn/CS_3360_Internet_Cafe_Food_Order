package sample.models;

import javafx.scene.image.Image;

public class Users {
    private String username;
    private String password;
    private int balance;

    public Users() {
        this.username = "";
        this.password = "";
        this.balance = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
