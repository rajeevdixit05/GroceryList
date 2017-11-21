package sample.demo.com.grocerylist.database;

/**
 * Created by rajeev on 20/11/17.
 */

public class GroceryTable {
    public static final String TABLE_ITEMS = "grocery";
    public static final String COLUMN_ID = "groceryId";
    public static final String COLUMN_NAME = "groceryName";
    public static final String[] ALL_COLUMNS=
            {COLUMN_ID, COLUMN_NAME };

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " TEXT," +
                    COLUMN_NAME + " TEXT" +
                    ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;
}
