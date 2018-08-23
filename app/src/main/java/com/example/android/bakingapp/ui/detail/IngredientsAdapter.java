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

package com.example.android.bakingapp.ui.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.IngredientsListItemBinding;
import com.example.android.bakingapp.model.Ingredient;

import java.util.List;

/**
 * {@link IngredientsAdapter} exposes a list of ingredients to a {@link RecyclerView}
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    /** Member variable for list of ingredients */
    private List<Ingredient> mIngredients;
    /** The selected recipe name */
    private String mRecipeName;
    /** The list of ingredients indices which exist in the shopping list database */
    private List<Integer> mIndices;

    /**
     * An on-click handler that we've defined to make it easy for a Fragment to interface with
     * our RecyclerView
     */
    private final IngredientsAdapterOnClickHandler mOnClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface IngredientsAdapterOnClickHandler {
        void onIngredientClick(int ingredientIndex);
    }

    /**
     * Constructor for the IngredientsAdapter
     *
     * @param ingredients The list of {@link Ingredient}s
     * @param onClickHandler The on-click handler for this adapter. This single handler is called
     *                           when an item is clicked
     * @param recipeName The selected recipe name
     * @param indices The list of checked ingredient indices
     */
    public IngredientsAdapter(List<Ingredient> ingredients, IngredientsAdapterOnClickHandler onClickHandler,
                              String recipeName, List<Integer> indices) {
        mIngredients = ingredients;
        mOnClickHandler = onClickHandler;
        mRecipeName = recipeName;
        mIndices = indices;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout.
     * @return A IngredientsViewHolder that holds the IngredientsListItemBinding
     */
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        IngredientsListItemBinding ingredientsItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.ingredients_list_item, parent, false);
        return new IngredientsViewHolder(ingredientsItemBinding);
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
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.bind(ingredient, position);
    }

    /**
     * This method simply return the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of ingredients
     */
    @Override
    public int getItemCount() {
        if (null == mIngredients) return 0;
        return mIngredients.size();
    }

    /**
     * This method is to add a list of {@link Ingredient}s
     *
     * @param ingredients the data source of the adapter
     */
    public void addAll(List<Ingredient> ingredients) {
        mIngredients.clear();
        mIngredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    /**
     * When data changes, updates the list of ingredients indices
     * and notifies the adapter to use the new values on it.
     *
     * @param indices The list of checked ingredient indices
     */
    public void setIndices(List<Integer> indices) {
        mIndices = indices;
        notifyDataSetChanged();
    }

    /**
     * Cache of the children views for a ingredient list item
     */
    public class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** This field is used for data binding */
        private IngredientsListItemBinding mIngredientsItemBinding;

        /**
         * Constructor for IngredientsViewHolder
         *
         * @param ingredientsItemBinding Used to access the layout's variables and views
         */
        public IngredientsViewHolder(IngredientsListItemBinding ingredientsItemBinding) {
            super(ingredientsItemBinding.getRoot());
            mIngredientsItemBinding = ingredientsItemBinding;

            // Call setOnClickListener on the ImageView
            mIngredientsItemBinding.ivAdd.setOnClickListener(this);
        }

        /**
         * This method will take a Ingredient object as input and use that ingredient to display
         * the appropriate text within a list item
         *
         * @param ingredient The ingredient object
         * @param position The position of the item within the adapter's data set.
         */
        void bind(Ingredient ingredient, int position) {
            // Set the quantity, measure, ingredient to the TextView
            mIngredientsItemBinding.tvQuantity.setText(String.valueOf(ingredient.getQuantity()));
            mIngredientsItemBinding.tvMeasure.setText(ingredient.getMeasure());
            mIngredientsItemBinding.tvIngredient.setText(ingredient.getIngredient());

            // Change the image based on whether or not the ingredient exists in the shopping list
            if (mIndices.contains(position)) {
                // If the ingredient is in the shopping list, display a checked image.
                mIngredientsItemBinding.ivAdd.setImageResource(R.drawable.checked);
            } else {
                // Otherwise, display an unchecked image.
                mIngredientsItemBinding.ivAdd.setImageResource(R.drawable.unchecked);
            }
        }

        /**
         * Called whenever a user clicks on an image which represents the check state in the list.
         *
         * @param v The view that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnClickHandler.onIngredientClick(adapterPosition);
        }
    }
}
