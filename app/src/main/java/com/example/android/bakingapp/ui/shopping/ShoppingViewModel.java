package com.example.android.bakingapp.ui.shopping;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.data.RecipeRepository;
import com.example.android.bakingapp.data.ShoppingListEntry;

import java.util.List;

/**
 * {@link ViewModel} for ShoppingActivity
 */
public class ShoppingViewModel extends ViewModel {

    private final RecipeRepository mRepository;
    private final LiveData<List<ShoppingListEntry>> mList;

    public ShoppingViewModel(RecipeRepository repository) {
        mRepository = repository;
        mList = mRepository.getAllShoppingList();
    }

    /**
     * Returns {@link LiveData} with the list of ShoppingListEntries
     */
    public LiveData<List<ShoppingListEntry>> getList() {
        return mList;
    }
}
