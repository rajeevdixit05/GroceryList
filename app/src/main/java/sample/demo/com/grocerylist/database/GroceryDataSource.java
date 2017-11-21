package sample.demo.com.grocerylist.database;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sample.demo.com.grocerylist.model.GroceryItem;

/**
 * Created by rajeev on 20/11/17.
 */

public class GroceryDataSource {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public GroceryDataSource(Context mContext) {
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

    public GroceryItem createGroceryList (GroceryItem groceryItem) {
        ContentValues values = groceryItem.toValues();
        mDatabase.insert(GroceryTable.TABLE_ITEMS, null, values);
        return groceryItem;
    }

    public long getGroceryListCount() {
        return DatabaseUtils.queryNumEntries(mDatabase,GroceryTable.TABLE_ITEMS);
    }

    public void seedDatabase(List<GroceryItem> groceryList) {
        long numItems= getGroceryListCount();
        if (numItems==0) {
            for (GroceryItem groceryItem : groceryList) {
                try {
                    createGroceryList(groceryItem);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<GroceryItem> getAllItems () {
        List<GroceryItem> groceryList = new ArrayList<>();
        Cursor cursor= mDatabase.query(GroceryTable.TABLE_ITEMS, GroceryTable.ALL_COLUMNS, null, null, null, null, null );
        Log.d("Cursor","here: "+cursor.moveToNext());
        while (cursor.moveToNext()) {
            GroceryItem item = new GroceryItem();
            item.setGroceryId(cursor.getString(cursor.getColumnIndex(GroceryTable.COLUMN_ID)));
            item.setGroceryItemName(cursor.getString(cursor.getColumnIndex(GroceryTable.COLUMN_NAME)));
            groceryList.add(item);
        }
        return  groceryList;
    }

}
