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

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final RecipeAdapterOnClickHandler mOnClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void  onItemClick(Recipe recipe);
    }

    private List<Recipe> mRecipeList;

    /**
     * Constructor for RecipeAdapter
     * @param onClickHandler
     */
    public RecipeAdapter(List<Recipe> recipeList, RecipeAdapterOnClickHandler onClickHandler) {
        mRecipeList = recipeList;
        mOnClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipeListItemBinding recipeItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(recipeItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        if (null == mRecipeList) return 0;
        return mRecipeList.size();
    }

    public void addAll(List<Recipe> recipeList) {
        mRecipeList.clear();
        mRecipeList.addAll(recipeList);
        notifyDataSetChanged();
    }

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

        void bind(Recipe recipe) {
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
