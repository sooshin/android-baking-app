package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.model.Recipe;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;

public class DetailActivity extends AppCompatActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRecipe = getRecipeData();
        setTitle(mRecipe.getName());

    }

    private Recipe getRecipeData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_RECIPE)) {
                Bundle b = intent.getBundleExtra(EXTRA_RECIPE);
                mRecipe = b.getParcelable(EXTRA_RECIPE);
            }
        }
        return mRecipe;
    }
}
