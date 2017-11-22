package sample.demo.com.grocerylist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sample.demo.com.grocerylist.database.ItemsDataSource;
import sample.demo.com.grocerylist.keepgrocery.AddGroceryListItemsActivity;
import sample.demo.com.grocerylist.model.DataItem;
import sample.demo.com.grocerylist.model.GroceryItem;

/**
 * Created by rajeev on 21/11/17.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private List<DataItem> mItem;
    private Context mContext;
    ItemsDataSource mItemSource;
    ItemListAdapter adapter;
    public static final String ITEM_KEY = "item_key";

    public ItemListAdapter(AddGroceryListItemsActivity addGroceryListItemsActivity, List<DataItem> itemList) {
        this.mContext = addGroceryListItemsActivity;
        this.mItem = itemList;
        this.adapter=this;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Select The Action");
                final CharSequence[] items = {"Edit Item", "Delete Item", "Mark Done"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item1) {
                        Toast.makeText(mContext, "You clicked " + item.getItemName(),
                                Toast.LENGTH_SHORT).show();
                        mItemSource= new ItemsDataSource(mContext);
                        mItemSource.open();
                        switch (item1) {
                            case 0:
                                break;
                            case 1:
                                if (mItem != null) {
                                    mItemSource.deleteItemList(item);
                                    adapter.notifyDataSetChanged();
                                }
                                break;
                            case 2:
                                item.setStatus(1);
                                mItemSource.updateItemList(item);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public interface ItemListAdapterCallback {
        public void refreshDisplay();
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

