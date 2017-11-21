package sample.demo.com.grocerylist.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import sample.demo.com.grocerylist.database.GroceryTable;

/**
 * Created by rajeev on 20/11/17.
 */

public class GroceryItem implements Parcelable {
    private String groceryId;
    private String groceryItemName;

    public GroceryItem () {

    }

    public GroceryItem(String groceryId, String groceryItemName) {
        if (groceryId==null) {
            groceryId= UUID.randomUUID().toString().replaceAll("[^a-zA-z0-9" + "]","");
        }
        this.groceryId = groceryId;
        this.groceryItemName = groceryItemName;
    }

    public String getGroceryId() {
        return groceryId;
    }

    public void setGroceryId(String groceryId) {
        this.groceryId = groceryId;
    }

    public String getGroceryItemName() {
        return groceryItemName;
    }

    public void setGroceryItemName(String groceryItemName) {
        this.groceryItemName = groceryItemName;
    }

    public ContentValues toValues() {
        ContentValues values= new ContentValues(3);
        values.put(GroceryTable.COLUMN_ID,groceryId);
        values.put(GroceryTable.COLUMN_NAME,groceryItemName);
        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groceryId);
        dest.writeString(this.groceryItemName);
    }

    protected GroceryItem(Parcel in) {
        this.groceryId = in.readString();
        this.groceryItemName = in.readString();
    }

    public static final Parcelable.Creator<GroceryItem> CREATOR = new Parcelable.Creator<GroceryItem>() {
        @Override
        public GroceryItem createFromParcel(Parcel source) {
            return new GroceryItem(source);
        }

        @Override
        public GroceryItem[] newArray(int size) {
            return new GroceryItem[size];
        }
    };

    @Override
    public String toString() {
        return "GroceryItem{" +
                "groceryId='" + groceryId + '\'' +
                ", groceryItemName='" + groceryItemName + '\'' +
                '}';
    }
}
