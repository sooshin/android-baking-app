package com.example.android.bakingapp.ui.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.FragmentMasterListIngredientsBinding;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;

/**
 * The MasterListIngredientsFragment displays ingredients of the recipe.
 */
public class MasterListIngredientsFragment extends Fragment{

    /** Member variable for the Recipe */
    private Recipe mRecipe;

    /** Member variable for IngredientsAdapter */
    private IngredientsAdapter mIngredientsAdapter;

    /** This field is used for data binding */
    private FragmentMasterListIngredientsBinding mMasterListBinding;

    /**
     *  Mandatory empty constructor
     */
    public MasterListIngredientsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Instantiate FragmentMasterListIngredientsBinding using DataBindingUtil
        mMasterListBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_master_list_ingredients, container, false);
        View rootView = mMasterListBinding.getRoot();

        // Get the recipe data from the MainActivity
        mRecipe = getRecipeData();
        List<Ingredient> ingredients = new ArrayList<>();

        // The IngredientsAdapter is responsible for displaying each ingredient in the list
        mIngredientsAdapter = new IngredientsAdapter(ingredients);

        // A LinearLayoutManager is responsible for measuring and positioning item views within a
        // RecyclerView into a linear list.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        // Set the layout manager to the RecyclerView
        mMasterListBinding.rvIngredients.setLayoutManager(layoutManager);
        // Use this setting to improve performance if you know that changes in content do not
        // change the child layout size in the RecyclerView
        mMasterListBinding.rvIngredients.setHasFixedSize(true);

        // Set the IngredientsAdapter to the RecyclerView
        mMasterListBinding.rvIngredients.setAdapter(mIngredientsAdapter);

        // Add a list of ingredients to the IngredientsAdapter
        mIngredientsAdapter.addAll(mRecipe.getIngredients());

        // Display the number of servings
//        setNumServings();

        return rootView;
    }

    /**
     * Gets recipe data from the MainActivity
     *
     * @return The Recipe data
     */
    private Recipe getRecipeData() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_RECIPE)) {
                // Receive the Recipe object which contains ID, name, ingredients, steps, servings,
                // and image of the recipe
                Bundle b = intent.getBundleExtra(EXTRA_RECIPE);
                mRecipe = b.getParcelable(EXTRA_RECIPE);
            }
        }
        return mRecipe;
    }

    /**
     * Display the number of servings
     */
//    private void setNumServings() {
//        int numServings = mRecipe.getServings();
//        mMasterListBinding.tvServings.setText(String.valueOf(numServings));
//    }
}
