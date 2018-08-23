/*
 *  Copyright 2018 Soojeong Shin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

/**
 * {@link ShoppingAdapter} exposes the shopping lists to a {@link RecyclerView}.
 */
public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder> {

    /** Member variable for the list of {@link ShoppingListEntry}s */
    private List<ShoppingListEntry> mShoppingEntries;
    /** The context of the app */
    private Context mContext;

    /**
     * Constructor for ShoppingAdapter that accepts a list of ShoppingListEntry to display
     */
    public ShoppingAdapter(Context context) {
        mContext = context;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @return A new ShoppingViewHolder that holds the ShoppingListItemBinding
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
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        ShoppingListEntry shoppingEntry = mShoppingEntries.get(position);
        holder.bind(shoppingEntry);
    }

    /**
     * Returns the number of ShoppingEntries to display.
     */
    @Override
    public int getItemCount() {
        if (mShoppingEntries == null) return 0;
        return mShoppingEntries.size();
    }

    /**
     * When data changes, update the list of shoppingEntries
     * and notifies the adapter to use the new values on it.
     */
    public void setShoppingList(List<ShoppingListEntry> shoppingListEntries) {
        mShoppingEntries = shoppingListEntries;
        notifyDataSetChanged();
    }

    /**
     * Returns the list of ShoppingListEntries.
     */
    public List<ShoppingListEntry> getShoppingListEntries() {
        return mShoppingEntries;
    }

    /**
     * Cache of the children views for the shopping list item.
     */
    public class ShoppingViewHolder extends RecyclerView.ViewHolder {
        /** This field is used for data binding */
        private ShoppingListItemBinding mShoppingItemBinding;

        /**
         * Constructor for ShoppingViewHolder.
         *
         * @param shoppingItemBinding Used to access the layout's variables and views
         */
        public ShoppingViewHolder(ShoppingListItemBinding shoppingItemBinding) {
            super(shoppingItemBinding.getRoot());

            mShoppingItemBinding = shoppingItemBinding;
        }

        /**
         * Displays the shopping list data on the TextView.
         */
        void bind(ShoppingListEntry shoppingListEntry) {
            mShoppingItemBinding.tvRecipeName.setText(shoppingListEntry.getRecipeName());
            mShoppingItemBinding.tvQuantity.setText(String.valueOf(shoppingListEntry.getQuantity()));
            mShoppingItemBinding.tvMeasure.setText(shoppingListEntry.getMeasure());
            mShoppingItemBinding.tvIngredient.setText(shoppingListEntry.getIngredient());
        }
    }
}
