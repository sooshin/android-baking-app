package com.example.android.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.android.bakingapp.adapter.RecipeAdapter;
import com.example.android.bakingapp.databinding.ActivityMainBinding;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utilities.BakingInterface;
import com.example.android.bakingapp.utilities.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;

/**
 * The MainActivity displays the list of recipes
 */
public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    /** Tag for logging */
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecipeAdapter mRecipeAdapter;

    private List<Recipe> mRecipeList;

    /** This field is used for data binding **/
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMainBinding.rv.setLayoutManager(layoutManager);
        mMainBinding.rv.setHasFixedSize(true);
        mRecipeList = new ArrayList<>();

        mRecipeAdapter = new RecipeAdapter(mRecipeList, this);
        mMainBinding.rv.setAdapter(mRecipeAdapter);

        callRecipeResponse();
    }

    private void callRecipeResponse() {
        Retrofit retrofit = RetrofitClient.getClient();
        BakingInterface bakingInterface = retrofit.create(BakingInterface.class);

        Call<List<Recipe>> callRecipeList = bakingInterface.getRecipes();
        callRecipeList.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipeList = response.body();
                mRecipeAdapter.addAll(recipeList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(Recipe recipe) {
        // Wrap the parcelable into a bundle
        Bundle b = new Bundle();
        b.putParcelable(EXTRA_RECIPE, recipe);

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, b);
        startActivity(intent);
    }
}
