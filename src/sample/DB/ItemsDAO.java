package sample.DB;

import javafx.scene.image.Image;
import sample.models.Items;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsDAO implements DAO{
    private static Items createItems(ResultSet rs){
        Items i = new Items();
        try{
            i.setName(rs.getString("name"));
            i.setPrice(rs.getDouble("price"));
            i.setImgSrc(new Image(rs.getBlob("img").getBinaryStream()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return i;
    }

    // Get items from database to a list
    public static List<Items> getItems(String typeOfFood){
        String sql = "SELECT * FROM menudata." + typeOfFood +" order by id";
        List<Items> list = new ArrayList<>();

        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // Add items from database to list
            while (rs.next()){
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


}
