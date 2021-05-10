package sample.data;

import sample.models.Items;
import sample.models.OnCartItems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Data {
    private static List<OnCartItems> onCartItemList = new ArrayList<>();
    private static List<String> itemNameList = new ArrayList<>();

    public static List<OnCartItems> getOnCartItemList() {
        return onCartItemList;
    }

    public static void addItem(OnCartItems onCartItem) {
        onCartItemList.add(onCartItem);
        itemNameList.add(onCartItem.getName());
    }

    public static void removeItem(OnCartItems onCartItem) {
        onCartItemList.remove(onCartItem);
        itemNameList.remove(onCartItem.getName());
    }

    public static boolean isContain(OnCartItems onCartItem){
        return itemNameList.contains(onCartItem.getName());
    }
}
