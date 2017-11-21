package sample.demo.com.grocerylist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sample.demo.com.grocerylist.keepgrocery.AddGroceryListItemsActivity;
import sample.demo.com.grocerylist.model.DataItem;
import sample.demo.com.grocerylist.model.GroceryItem;

/**
 * Created by rajeev on 21/11/17.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private List<DataItem> mItem;
    private Context mContext;
    public static final String ITEM_KEY = "item_key";

    public ItemListAdapter(AddGroceryListItemsActivity addGroceryListItemsActivity, List<DataItem> itemList) {
        this.mContext = addGroceryListItemsActivity;
        this.mItem = itemList;
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
        final DataItem item = mItem.get(position);

        holder.tvName.setText(item.getItemName());

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "You long clicked " + item.getItemName(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public View mView;
        public ViewHolder(View item) {
            super(item);
            tvName = (android.widget.TextView) itemView.findViewById(R.id.itemList);
            mView = itemView;
        }
    }
}

