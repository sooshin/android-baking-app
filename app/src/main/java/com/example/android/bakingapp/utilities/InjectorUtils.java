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

package com.example.android.bakingapp.utilities;

import android.content.Context;

import com.example.android.bakingapp.AppExecutors;
import com.example.android.bakingapp.data.RecipeDatabase;
import com.example.android.bakingapp.data.RecipeRepository;
import com.example.android.bakingapp.ui.detail.IndicesViewModelFactory;
import com.example.android.bakingapp.ui.main.MainViewModelFactory;
import com.example.android.bakingapp.ui.shopping.ShoppingViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for BakingApp.
 */
public class InjectorUtils {

    public static RecipeRepository provideRepository(Context context) {
        RecipeDatabase database = RecipeDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        BakingInterface bakingInterface = RetrofitClient.getClient().create(BakingInterface.class);
        return RecipeRepository.getInstance(database.recipeDao(), bakingInterface, executors);
    }

    public static MainViewModelFactory provideMainViewModelFactory(Context context) {
        RecipeRepository repository = provideRepository(context);
        return new MainViewModelFactory(repository);
    }

    public static IndicesViewModelFactory provideIndicesViewModelFactory(
            Context context, String recipeName) {
        RecipeRepository repository = provideRepository(context);
        return new IndicesViewModelFactory(repository, recipeName);
    }

    public static ShoppingViewModelFactory provideListViewModelFactory(Context context) {
        RecipeRepository repository = provideRepository(context);
        return new ShoppingViewModelFactory(repository);
    }
}
