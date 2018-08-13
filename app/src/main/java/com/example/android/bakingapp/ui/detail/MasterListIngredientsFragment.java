package com.example.android.bakingapp.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingapp.AppExecutors;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeDatabase;
import com.example.android.bakingapp.data.ShoppingListEntry;
import com.example.android.bakingapp.databinding.FragmentMasterListIngredientsBinding;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;

/**
 * The MasterListIngredientsFragment displays ingredients of the recipe.
 */
public class MasterListIngredientsFragment extends Fragment implements
        IngredientsAdapter.IngredientsAdapterOnClickHandler {

    /** Member variable for the Recipe */
    private Recipe mRecipe;

    /** Member variable for IngredientsAdapter */
    private IngredientsAdapter mIngredientsAdapter;

    /** This field is used for data binding */
    private FragmentMasterListIngredientsBinding mMasterListBinding;

    /** ViewModel for MasterListIngredientsFragment */
    private IndicesViewModel mIndicesViewModel;

    /** Member variable for the RecipeDatabase */
    private RecipeDatabase mDb;

    /** The list of checked ingredient indices */
    private List<Integer> mIndices;

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

        // Create a new list of the ingredient and indices
        List<Ingredient> ingredients = new ArrayList<>();
        mIndices = new ArrayList<>();

        // The IngredientsAdapter is responsible for displaying each ingredient in the list
        mIngredientsAdapter = new IngredientsAdapter(ingredients, this,
                mRecipe.getName(), mIndices);

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

        // Get the RecipeDatabase instance
        mDb = RecipeDatabase.getInstance(this.getContext());

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get the recipe data from the MainActivity
        mRecipe = getRecipeData();

        // Setup indices view model
        setupIndicesViewModel(getActivity());
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
     * Every time the user data is updated, update the check state of the UI
     */
    private void setupIndicesViewModel(Context context) {
        // Get the ViewModel from the factory
        IndicesViewModelFactory factory = InjectorUtils.provideIndicesViewModelFactory(
                context, mRecipe.getName());
        mIndicesViewModel = ViewModelProviders.of(this, factory).get(IndicesViewModel.class);

        // Update the list of ingredient indices
        mIndicesViewModel.getIndices().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(@Nullable List<Integer> integers) {
                mIndices = integers;
                mIngredientsAdapter.setIndices(mIndices);
            }
        });
    }

    /**
     * This method is overridden by MasterListIngredientsFragment class in order to handle
     * RecyclerView item clicks.
     *
     * @param ingredientIndex Position of the ingredient in the list
     */
    @Override
    public void onIngredientClick(final int ingredientIndex) {
        // Get ShoppingListEntry based on current recipe name and ingredient
        final ShoppingListEntry currentShoppingListEntry = getCurrentShoppingListEntry(ingredientIndex);

        if (!mIndices.contains(ingredientIndex)) {
            // If the current ingredient index does not exist in the list of indices, insert it into the database.
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.recipeDao().insertIngredient(currentShoppingListEntry);
                }
            });
            Toast.makeText(this.getActivity(), "Added to a Shopping List", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    /**
     * Returns ShoppingListEntry based on recipe name and ingredient
     *
     * @param ingredientIndex Position of the ingredient in the list
     */
    private ShoppingListEntry getCurrentShoppingListEntry(int ingredientIndex) {
        // Get the ingredient
        Ingredient ingredient = mRecipe.getIngredients().get(ingredientIndex);

        return new ShoppingListEntry(mRecipe.getName(), ingredient.getQuantity(),
                ingredient.getMeasure(), ingredient.getIngredient(), ingredientIndex);
    }

    /**
     * Display the number of servings
     */
//    private void setNumServings() {
//        int numServings = mRecipe.getServings();
//        mMasterListBinding.tvServings.setText(String.valueOf(numServings));
//    }
}
