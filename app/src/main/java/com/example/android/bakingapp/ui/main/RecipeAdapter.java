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

package com.example.android.bakingapp.ui.main;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.RecipeListItemBinding;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.BakingUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.android.bakingapp.utilities.Constant.RECIPE_IMAGE_PADDING;

/**
 * {@link RecipeAdapter} exposes a list of recipes to a {@link RecyclerView}
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    /**
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final RecipeAdapterOnClickHandler mOnClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface RecipeAdapterOnClickHandler {
        void  onItemClick(Recipe recipe);
    }

    /** Member variable for the list of {@link Recipe}s */
    private List<Recipe> mRecipeList;

    /**
     * Constructor for RecipeAdapter that accepts a list of recipes to display
     *
     * @param recipeList The list of {@link Recipe}s
     * @param onClickHandler The on-click handler for this adapter. This single handler is called
     *                       when an item is clicked.
     */
    public RecipeAdapter(List<Recipe> recipeList, RecipeAdapterOnClickHandler onClickHandler) {
        mRecipeList = recipeList;
        mOnClickHandler = onClickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout.
     * @return A new RecipeViewHolder that holds the RecipeListItemBinding
     */
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipeListItemBinding recipeItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(recipeItemBinding);
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
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.bind(recipe, position);
    }


    /**
     * This method simply return the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of recipes
     */
    @Override
    public int getItemCount() {
        if (null == mRecipeList) return 0;
        return mRecipeList.size();
    }

    /**
     * This method is to add a list of {@link Recipe}s
     *
     * @param recipeList The recipe list is the data source of the adapter
     */
    public void addAll(List<Recipe> recipeList) {
        mRecipeList.clear();
        mRecipeList.addAll(recipeList);
        notifyDataSetChanged();
    }

    /**
     * Caches of the childeren views for a recipe list item.
     */
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** This field is used for data binding */
        private RecipeListItemBinding mRecipeItemBinding;

        /**
         * Constructor for RecipeViewHolder
         *
         * @param recipeItemBinding Used to access the layout's variables and views
         */
        public RecipeViewHolder(RecipeListItemBinding recipeItemBinding) {
            super(recipeItemBinding.getRoot());
            mRecipeItemBinding = recipeItemBinding;

            // Call setOnClickListener on the View
            itemView.setOnClickListener(this);
        }

        /**
         * This method will take a Recipe object as input and use that recipe to display the appropriate
         * text within a list item.
         *
         * @param recipe The recipe object
         */
        void bind(Recipe recipe, int position) {
            // Set the name of the recipe
            mRecipeItemBinding.tvName.setText(recipe.getName());

            String imageUrl = recipe.getImage();
            if (imageUrl.isEmpty()) {
                // If the image URL does not exist, set background color of the ImageView differently
                // depending on the position.
                int imageColorResourceId = BakingUtils.getImageBackGroundColor(itemView.getContext(), position);
                mRecipeItemBinding.ivImage.setBackgroundColor(imageColorResourceId);

                // Set image resource differently depending on the position
                int imageResourceId = BakingUtils.getImageResource(position);
                mRecipeItemBinding.ivImage.setImageResource(imageResourceId);

                // Set padding for resizing image
                mRecipeItemBinding.ivImage.setPadding(RECIPE_IMAGE_PADDING,
                        RECIPE_IMAGE_PADDING, RECIPE_IMAGE_PADDING, RECIPE_IMAGE_PADDING);

            } else {
                // If the image URL exists, use the Picasso library to upload the image
                Picasso.with(itemView.getContext())
                        .load(imageUrl)
                        .error(R.drawable.recipe_error_image)
                        .placeholder(R.drawable.recipe_error_image)
                        .into(mRecipeItemBinding.ivImage);
            }

            // Set the background color of the TextView displaying the recipe name
            int textColorResourceId = BakingUtils.getTextBackGroundColor(itemView.getContext(), position);
            mRecipeItemBinding.tvName.setBackgroundColor(textColorResourceId);

        }

        /**
         * Called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeList.get(adapterPosition);
            mOnClickHandler.onItemClick(recipe);
        }
    }
}
