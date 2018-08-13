package com.example.android.bakingapp.ui.shopping;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.ShoppingListEntry;
import com.example.android.bakingapp.databinding.ShoppingListItemBinding;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder> {

    private List<ShoppingListEntry> mShoppingEntries;

    private Context mContext;

    public ShoppingAdapter(Context context) {
        mContext = context;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ShoppingListItemBinding shoppingItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.shopping_list_item, parent, false);
        return new ShoppingViewHolder(shoppingItemBinding);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        ShoppingListEntry shoppingEntry = mShoppingEntries.get(position);
        holder.bind(shoppingEntry);
    }

    @Override
    public int getItemCount() {
        if (mShoppingEntries == null) return 0;
        return mShoppingEntries.size();
    }

    public void setShoppingList(List<ShoppingListEntry> shoppingListEntries) {
        mShoppingEntries = shoppingListEntries;
        notifyDataSetChanged();
    }

    public List<ShoppingListEntry> getShoppingListEntries() {
        return mShoppingEntries;
    }

    public class ShoppingViewHolder extends RecyclerView.ViewHolder {

        private ShoppingListItemBinding mShoppingItemBinding;


        public ShoppingViewHolder(ShoppingListItemBinding shoppingItemBinding) {
            super(shoppingItemBinding.getRoot());

            mShoppingItemBinding = shoppingItemBinding;
        }

        void bind(ShoppingListEntry shoppingListEntry) {
            mShoppingItemBinding.tvRecipeName.setText(shoppingListEntry.getRecipeName());
            mShoppingItemBinding.tvQuantity.setText(String.valueOf(shoppingListEntry.getQuantity()));
            mShoppingItemBinding.tvMeasure.setText(shoppingListEntry.getMeasure());
            mShoppingItemBinding.tvIngredient.setText(shoppingListEntry.getIngredient());
        }
    }
}
