package sample.demo.com.grocerylist.keepgrocery;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import sample.demo.com.grocerylist.GroceryListAdapter;
import sample.demo.com.grocerylist.ItemListAdapter;
import sample.demo.com.grocerylist.R;
import sample.demo.com.grocerylist.SampleDataProvider;
import sample.demo.com.grocerylist.database.DBHelper;
import sample.demo.com.grocerylist.database.GroceryDataSource;
import sample.demo.com.grocerylist.database.ItemsDataSource;
import sample.demo.com.grocerylist.model.DataItem;
import sample.demo.com.grocerylist.model.GroceryItem;

public class AddGroceryListItemsActivity extends AppCompatActivity {
    ItemsDataSource mItemSource;
    List<String> dataItem;
    List<DataItem> itemList;
    GroceryItem groceryItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery_list_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        groceryItem= getIntent().getExtras().getParcelable(GroceryListAdapter.GROCERY_KEY);
        if (groceryItem != null) {
            Toast.makeText(this,"Recieved item: "+groceryItem.getGroceryItemName(),Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Didn't recieve any data",Toast.LENGTH_SHORT).show();
        }
        toolbar.setTitle(groceryItem.getGroceryItemName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mItemSource= new ItemsDataSource(this);
        mItemSource.open();
        if (itemList != null) {
            mItemSource.seedDatabase(itemList);
        }

        updateItemsList();
    }

    private void updateItemsList() {
        String groceryId= groceryItem.getGroceryId();
        if (groceryId != null) {
            List<DataItem> itemList = mItemSource.getGroceryListItems(groceryId);

            ItemListAdapter adapter = new ItemListAdapter(this, itemList);
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            boolean grid = settings.getBoolean(getString(R.string.pref_display_grid), false);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.itemList);
            if (grid) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            }

            recyclerView.setAdapter(adapter);
        }

    }

    private void createGroceryList() {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new item in Grocery List")
                .setMessage("What items you want to add next?")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataItem item= new DataItem(null,String.valueOf(taskEditText.getText()),groceryItem.getGroceryId());
                        try {
                            mItemSource.createItemList(item);
                        } catch (SQLiteException e) {
                            e.printStackTrace();
                        }
                        updateItemsList();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_items_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_add_task:
                createGroceryList();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
