package com.example.android.bakingapp.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.data.RecipeRepository;

import java.util.List;

/**
 * {@link ViewModel} for {@link MasterListIngredientsFragment}
 */
public class IndicesViewModel extends ViewModel {

    private final RecipeRepository mRepository;

    /** The list of ingredient indices which exist in the shopping list database */
    private LiveData<List<Integer>> mIndices;

    public IndicesViewModel(RecipeRepository repository, String recipeName) {
        mRepository = repository;
        mIndices = mRepository.getIndices(recipeName);
    }

    /**
     * Returns {@link LiveData} with the list of ingredient indices which exist in
     * the shopping list database.
     */
    public LiveData<List<Integer>> getIndices() {
        return mIndices;
    }
}
