package com.example.android.bakingapp.ui.shopping;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.bakingapp.data.RecipeRepository;

/**
 *  Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link RecipeRepository}
 */
public class ShoppingViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final RecipeRepository mRepository;

    public ShoppingViewModelFactory(RecipeRepository repository) {
        mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ShoppingViewModel(mRepository);
    }
}
