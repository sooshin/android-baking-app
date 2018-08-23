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

package com.example.android.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.android.bakingapp.AppExecutors;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.BakingInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * RecipeRepository is responsible for handling data operations in BakingApp. Acts as a mediator
 * between {@link BakingInterface} and {@link RecipeDao}
 */
public class RecipeRepository {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static RecipeRepository sInstance;
    private final RecipeDao mRecipeDao;
    private final BakingInterface mBakingInterface;
    private final AppExecutors mExecutors;

    private RecipeRepository(RecipeDao recipeDao, BakingInterface bakingInterface,
                             AppExecutors executors) {
        mRecipeDao = recipeDao;
        mBakingInterface = bakingInterface;
        mExecutors = executors;

    }

    public synchronized static RecipeRepository getInstance(
            RecipeDao recipeDao, BakingInterface bakingInterface, AppExecutors executors) {
        Timber.d("Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                Timber.d("Making new repository");
                sInstance = new RecipeRepository(recipeDao, bakingInterface, executors);
            }
        }
        return sInstance;
    }

    /**
     * Make a network request by calling enqueue and provide a LiveData of recipe lists for ViewModel
     */
    public LiveData<List<Recipe>> getRecipeListFromNetwork() {
        final MutableLiveData<List<Recipe>> recipeListData = new MutableLiveData<>();

        mBakingInterface.getRecipes()
                .enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        if (response.isSuccessful()) {
                            List<Recipe> recipeList = response.body();
                            recipeListData.setValue(recipeList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        recipeListData.setValue(null);
                        Timber.e("Failed getting List of Recipes" + t.getMessage());
                    }
                });
        return recipeListData;
    }

    /**
     * Returns {@link LiveData} with the list of ShoppingEntries directly from the database
     */
    public LiveData<List<ShoppingListEntry>> getAllShoppingList() {
        return mRecipeDao.getAllShoppingList();
    }

    /**
     * Returns {@link LiveData} with the list of ingredient indices which exist in
     * the shopping list database.
     *
     * @param recipeName The recipe name
     */
    public LiveData<List<Integer>> getIndices(String recipeName) {
        return mRecipeDao.getIndices(recipeName);
    }
}

