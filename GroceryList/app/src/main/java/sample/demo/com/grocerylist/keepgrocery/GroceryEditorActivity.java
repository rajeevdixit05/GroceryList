package sample.demo.com.grocerylist.keepgrocery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import sample.demo.com.grocerylist.model.GroceryItem;

public class GroceryEditorActivity extends AppCompatActivity {
    private GroceryItem groceryItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    private void saveAndFinish() {

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndFinish();
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        saveAndFinish();
    }
}
