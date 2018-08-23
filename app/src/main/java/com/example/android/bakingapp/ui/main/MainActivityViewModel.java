/*
 *  Copyright 2018 Soojeong Shin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
    private LiveData<List<Recipe>> mRecipes;

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

    /**
     * Sets a new value for the list of recipes
     */
    public void setRecipes() {
        mRecipes = mRepository.getRecipeListFromNetwork();
    }
}
