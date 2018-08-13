package com.example.android.bakingapp.utilities;

import android.content.Context;

import com.example.android.bakingapp.AppExecutors;
import com.example.android.bakingapp.data.RecipeDatabase;
import com.example.android.bakingapp.data.RecipeRepository;
import com.example.android.bakingapp.ui.detail.IndicesViewModelFactory;
import com.example.android.bakingapp.ui.main.MainViewModelFactory;
import com.example.android.bakingapp.ui.shopping.ShoppingViewModelFactory;

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
