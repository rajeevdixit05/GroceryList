package sample.demo.com.grocerylist.database;

/**
 * Created by rajeev on 21/11/17.
 */

public class ItemTable {
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "itemId";
    public static final String COLUMN_NAME = "itemName";
    public static final String COLUMN_GROCERY_ID = "groceryId";
    public static final String[] ALL_COLUMNS=
            {COLUMN_ID, COLUMN_NAME, COLUMN_GROCERY_ID };

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " TEXT," +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_GROCERY_ID + " TEXT" +
                    ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;
}
