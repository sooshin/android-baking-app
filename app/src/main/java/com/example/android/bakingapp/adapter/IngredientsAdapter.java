package com.example.android.bakingapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.IngredientsListItemBinding;
import com.example.android.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    /** Member variable for list of ingredients*/
    private List<Ingredient> mIngredients;

    /**
     * Constructor for the IngredientsAdapter
     *
     * @param ingredients The list of ingredients
     */
    public IngredientsAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        IngredientsListItemBinding ingredientsItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.ingredients_list_item, parent, false);
        return new IngredientsViewHolder(ingredientsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        if (null == mIngredients) return 0;
        return mIngredients.size();
    }

    public void addAll(List<Ingredient> ingredients) {
        mIngredients.clear();
        mIngredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private IngredientsListItemBinding mIngredientsItemBinding;

        public IngredientsViewHolder(IngredientsListItemBinding ingredientsItemBinding) {
            super(ingredientsItemBinding.getRoot());
            mIngredientsItemBinding = ingredientsItemBinding;
        }

        void bind(Ingredient ingredient) {
            mIngredientsItemBinding.tvQuantity.setText(String.valueOf(ingredient.getQuantity()));
            mIngredientsItemBinding.tvMeasure.setText(ingredient.getMeasure());
            mIngredientsItemBinding.tvIngredient.setText(ingredient.getIngredient());
        }
    }
}
