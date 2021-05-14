package sample.DB;

import javafx.scene.image.Image;
import sample.models.Items;
import sample.models.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsDAO implements DAO {
    private static Items createItems(ResultSet rs) {
        Items i = new Items();
        try {
            i.setName(rs.getString("name"));
            i.setPrice(rs.getInt("price"));
            i.setImgSrc(new Image(rs.getBlob("img").getBinaryStream()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    private static Users createUsers(ResultSet rs) {
        Users u = new Users();
        try {
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setBalance(rs.getInt("balance"));
            u.setEmail(rs.getString("email"));
            u.setPhoneNumber(rs.getString("phone"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return u;
    }

    // Get items from database to a list
    public static List<Items> getItems(String typeOfFood) {
        String sql = "SELECT * FROM menudata." + typeOfFood + " order by id";
        List<Items> list = new ArrayList<>();

        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // Add items from database to list
            while (rs.next()) {
                Items i = createItems(rs);
                list.add(i);
            }

            rs.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM menudata.users order by username";

        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // Check if user is in database
            while (rs.next()) {
                Users databaseUser = createUsers(rs);
                if (databaseUser.getUsername().equals(username)
                        && databaseUser.getPassword().equals(password)) {
                    rs.close();
                    connection.close();
                    return true;
                }
            }
            rs.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static Users getUserInfo(String username, String password) {
        String sql = "SELECT * FROM menudata.users order by username";
        Users databaseUser = new Users();
        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // Check if user is in database
            while (rs.next()) {
                databaseUser = createUsers(rs);
                if (databaseUser.getUsername().equals(username)
                        && databaseUser.getPassword().equals(password)) {
                    break;
                }
            }
            rs.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return databaseUser;
    }

    public static void updateUserInfo(Users user) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "UPDATE users SET password = ?, balance = ? WHERE username = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, user.getPassword());
            preparedStmt.setInt(2, user.getBalance());
            preparedStmt.setString(3, user.getUsername());

            preparedStmt.executeUpdate();

            connection.close();
            preparedStmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
