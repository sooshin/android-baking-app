package com.example.android.bakingapp.ui.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    /**
     * Constructor for the IngredientsAdapter
     *
     * @param ingredients The list of ingredients
     */
    public IngredientsAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
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
        holder.bind(ingredient);
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
     * Cache of the children views for a ingredient list item
     */
    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
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
        }

        /**
         * This method will take a Ingredient object as input and use that ingredient to display
         * the appropriate text within a list item
         *
         * @param ingredient The ingredient object
         */
        void bind(Ingredient ingredient) {
            // Set the quantity, measure, ingredient to the TextView
            mIngredientsItemBinding.tvQuantity.setText(String.valueOf(ingredient.getQuantity()));
            mIngredientsItemBinding.tvMeasure.setText(ingredient.getMeasure());
            mIngredientsItemBinding.tvIngredient.setText(ingredient.getIngredient());
        }
    }
}
