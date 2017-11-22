package sample.demo.com.grocerylist.keepgrocery;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sample.demo.com.grocerylist.GroceryListAdapter;
import sample.demo.com.grocerylist.R;
import sample.demo.com.grocerylist.SampleDataProvider;
import sample.demo.com.grocerylist.database.DBHelper;
import sample.demo.com.grocerylist.database.GroceryDataSource;
import sample.demo.com.grocerylist.model.GroceryItem;

public class GroceryListActivity extends AppCompatActivity {
    private static final int EDITOR_ACTIVITY_REQUEST = 1001;
    private static final int MENU_DELETE_ID = 1002;
    private int currentGroceryId;
    List<String> groceryItem;
    GroceryDataSource mDataSource;
    List<GroceryItem> groceryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDataSource= new GroceryDataSource(this);
        mDataSource.open();

        if (groceryList !=null) {
            mDataSource.seedDatabase(groceryList);
        }


        ImageButton fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroceryList();

            }
        });
        refreshDisplay();

    }

    private void refreshDisplay() {
        List<GroceryItem> groceryList = mDataSource.getAllItems();
        GroceryListAdapter adapter = new GroceryListAdapter(this, groceryList);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean grid = settings.getBoolean(getString(R.string.pref_display_grid), false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.groceryList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        if (grid) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 0));
        }

        recyclerView.setAdapter(adapter);
    }

    private void createGroceryList() {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new Grocery List")
                .setMessage("What items you want to add next?")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (taskEditText.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(),"Can't add empty item",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            GroceryItem grocery = new GroceryItem(null, String.valueOf(taskEditText.getText()));
                            try {
                                mDataSource.createGroceryList(grocery);
                            } catch (SQLiteException e) {
                                e.printStackTrace();
                            }
                            refreshDisplay();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentGroceryId = (int)info.id;
        menu.add(0, EDITOR_ACTIVITY_REQUEST, 0, R.string.edit);
        menu.add(0, MENU_DELETE_ID, 0, R.string.delete);
    }

}
