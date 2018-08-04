package com.example.android.bakingapp.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.data.RecipeEntry;
import com.example.android.bakingapp.data.RecipeRepository;

import java.util.List;

/**
 * {@link ViewModel} for MainActivity
 */
public class MainActivityViewModel extends ViewModel {

    private final RecipeRepository mRepository;
    private final LiveData<List<RecipeEntry>> mRecipes;

    public MainActivityViewModel(RecipeRepository repository) {
        mRepository = repository;
        mRecipes = mRepository.getAllRecipes();
    }

    /**
     * Returns LiveData of the list of RecipeEntries
     */
    public LiveData<List<RecipeEntry>> getRecipes() {
        return mRecipes;
    }
}
