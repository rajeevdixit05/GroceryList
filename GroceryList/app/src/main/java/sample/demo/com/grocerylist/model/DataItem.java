package sample.demo.com.grocerylist.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import sample.demo.com.grocerylist.database.GroceryTable;
import sample.demo.com.grocerylist.database.ItemTable;

/**
 * Created by rajeev on 20/11/17.
 */

public class DataItem implements Parcelable {
    private String itemId;
    private String itemName;
    private String groceryId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getGroceryId() {
        return groceryId;
    }

    public void setGroceryId(String itemCategory) {
        this.groceryId = itemCategory;
    }

    public DataItem () {

    }

    public DataItem(String itemId, String itemName, String groceryId) {
        if (itemId==null) {
            itemId= UUID.randomUUID().toString().replaceAll("[^a-zA-z0-9" + "]","");
        }
        this.itemId = itemId;
        this.itemName = itemName;
        this.groceryId = groceryId;
    }

    public ContentValues toValues() {
        ContentValues values= new ContentValues(3);
        values.put(ItemTable.COLUMN_ID,itemId);
        values.put(ItemTable.COLUMN_NAME,itemName);
        values.put(ItemTable.COLUMN_GROCERY_ID,groceryId);
        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.itemName);
        dest.writeString(this.groceryId);
    }

    protected DataItem(Parcel in) {
        this.itemId = in.readString();
        this.itemName = in.readString();
        this.groceryId = in.readString();
    }

    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel source) {
            return new DataItem(source);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };

    @Override
    public String toString() {
        return "DataItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", groceryId='" + groceryId + '\'' +
                '}';
    }
}
