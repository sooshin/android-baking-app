package com.example.android.bakingapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.RecipeListItemBinding;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

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
        holder.bind(recipe);
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
        void bind(Recipe recipe) {
            // Set the name of the recipe
            mRecipeItemBinding.tvName.setText(recipe.getName());
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
