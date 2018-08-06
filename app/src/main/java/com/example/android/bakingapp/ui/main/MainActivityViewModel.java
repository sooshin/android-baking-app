package com.example.android.bakingapp.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.data.RecipeRepository;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

/**
 * {@link ViewModel} for MainActivity
 */
public class MainActivityViewModel extends ViewModel {

    private final RecipeRepository mRepository;
    private final LiveData<List<Recipe>> mRecipes;

    public MainActivityViewModel(RecipeRepository repository) {
        mRepository = repository;
        mRecipes = mRepository.getRecipeListFromNetwork();
    }

    /**
     * Returns LiveData of the list of Recipes
     */
    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }
}
