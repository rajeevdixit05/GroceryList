package sample.demo.com.grocerylist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sample.demo.com.grocerylist.model.DataItem;
import sample.demo.com.grocerylist.model.GroceryItem;

/**
 * Created by rajeev on 21/11/17.
 */

public class ItemsDataSource {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public ItemsDataSource(Context mContext) {
        this.mContext = mContext;
        mDbHelper= new DBHelper(mContext);
        mDatabase=mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase=mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public DataItem createItemList (DataItem dataItem) {
        ContentValues values = dataItem.toValues();
        mDatabase.insert(ItemTable.TABLE_ITEMS, null, values);
        return dataItem;
    }
    public long getItemListCount() {
        return DatabaseUtils.queryNumEntries(mDatabase,ItemTable.TABLE_ITEMS);
    }

    public void seedDatabase(List<DataItem> itemList) {
        long numItems= getItemListCount();
        if (numItems==0) {
            for (DataItem item : itemList) {
                try {
                    createItemList(item);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<DataItem> getAllItems () {
        List<DataItem> itemList = new ArrayList<>();
        Cursor cursor= mDatabase.query(ItemTable.TABLE_ITEMS, ItemTable.ALL_COLUMNS, null, null, null, null, null );
        while (cursor.moveToNext()) {
            DataItem item = new DataItem();
            item.setItemId(cursor.getString(cursor.getColumnIndex(ItemTable.COLUMN_ID)));
            item.setItemName(cursor.getString(cursor.getColumnIndex(ItemTable.COLUMN_NAME)));
            item.setGroceryId(cursor.getString(cursor.getColumnIndex(ItemTable.COLUMN_GROCERY_ID)));
            itemList.add(item);
        }
        return itemList;
    }

    public List<DataItem> getGroceryListItems (String grocerId) {
        String whereClause = "groceryId=?";
        String [] whereArgs = {grocerId};
        List<DataItem> itemList = new ArrayList<>();

        try {
            Cursor cursor= mDatabase.query(ItemTable.TABLE_ITEMS, ItemTable.ALL_COLUMNS, whereClause, whereArgs, null, null, null );
            while (cursor.moveToNext()) {
                DataItem item = new DataItem();
                item.setItemId(cursor.getString(cursor.getColumnIndex(ItemTable.COLUMN_ID)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(ItemTable.COLUMN_NAME)));
                item.setGroceryId(cursor.getString(cursor.getColumnIndex(ItemTable.COLUMN_GROCERY_ID)));
                itemList.add(item);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return itemList;
    }
}
