package sample.demo.com.grocerylist;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sample.demo.com.grocerylist.model.DataItem;
import sample.demo.com.grocerylist.model.GroceryItem;

/**
 * Created by rajeev on 20/11/17.
 */

public class SampleDataProvider {
    public static List<GroceryItem> groceryList;
    public static List<DataItem> itemList;
    public static Map<String, GroceryItem> groceryMap;
    public static Map<String, DataItem> dataItemMap;

    static {
        groceryList = new ArrayList<>();
        itemList = new ArrayList<>();
        groceryMap = new HashMap<>();
        dataItemMap= new HashMap<>();

        addItem(new GroceryItem("1", "Grocery 1"));
        addItem(new GroceryItem("1", "Grocery 2 "));
        addItem(new GroceryItem("1", "Grocery 3 "));
        addItem(new GroceryItem("1", "Grocery 4 "));
        addItem(new GroceryItem("1", "Grocery 5 "));

        addItem(new DataItem(null, "Fan", "1"));
        addItem(new DataItem(null, "Refrigerator ", "2"));
        addItem(new DataItem(null, "Bulb ", "2"));
        addItem(new DataItem(null, "Iron ", "2"));
    }

    private static void addItem(GroceryItem item) {
        groceryList.add(item);
        groceryMap.put(item.getGroceryId(), item);
    }

    private static void addItem(DataItem item) {
        itemList.add(item);
        dataItemMap.put(item.getItemId(), item);
    }


}
