package sample.demo.com.grocerylist;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import sample.demo.com.grocerylist.keepgrocery.AddGroceryListItemsActivity;
import sample.demo.com.grocerylist.keepgrocery.GroceryListActivity;
import sample.demo.com.grocerylist.model.GroceryItem;

/**
 * Created by rajeev on 20/11/17.
 */

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {
    public static final String GROCERY_KEY = "grocery_key";
    private Context mContext;
    private List<GroceryItem> mGrocery;

    public GroceryListAdapter(GroceryListActivity groceryListActivity, List<GroceryItem> groceryList) {
        this.mContext = groceryListActivity;
        this.mGrocery = groceryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GroceryItem grocery = mGrocery.get(position);

        holder.tvName.setText(grocery.getGroceryItemName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "You selected " + item.getItemName(),
//                        Toast.LENGTH_SHORT).show();
//                String groceryId = grocery.getGroceryId();
                Intent intent = new Intent(mContext, AddGroceryListItemsActivity.class);
                intent.putExtra(GROCERY_KEY, grocery);
                mContext.startActivity(intent);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "You long clicked " + grocery.getGroceryItemName(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGrocery.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public View mView;
        public ViewHolder(View groceryView) {
            super(groceryView);
            tvName = (android.widget.TextView) itemView.findViewById(R.id.grocery_title);
            mView = itemView;
        }
    }
}
