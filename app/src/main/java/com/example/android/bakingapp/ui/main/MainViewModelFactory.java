package com.example.android.bakingapp.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.bakingapp.data.RecipeRepository;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link RecipeRepository}
 */
public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final RecipeRepository mRepository;

    public MainViewModelFactory(RecipeRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository);
    }
}
