package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.fragment.MasterListStepsFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;
import static com.example.android.bakingapp.utilities.Constant.EXTRA_STEP;

/**
 * The DetailActivity is responsible for displaying the ingredients and steps for a selected
 * {@link Recipe}.
 */
public class DetailActivity extends AppCompatActivity implements MasterListStepsFragment.OnStepClickListener {

    /** Member variable for the recipe */
    private Recipe mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the recipe data from the MainActivity
        mRecipe = getRecipeData();

        // Set the title for a selected recipe
        setTitle(mRecipe.getName());
    }

    /**
     * Gets recipe data from the MainActivity
     *
     * @return The Recipe data
     */
    private Recipe getRecipeData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_RECIPE)) {
                // Receive the Recipe object which contains ID, name, ingredients, steps, servings,
                // and image of the recipe
                Bundle b = intent.getBundleExtra(EXTRA_RECIPE);
                mRecipe = b.getParcelable(EXTRA_RECIPE);
            }
        }
        return mRecipe;
    }

    /**
     * Define the behavior for onStepSelected
     *
     * @param step The Step object which contains a step ID, short description, description, video URL,
     *             and thumbnail URL of the step
     */
    @Override
    public void onStepSelected(Step step) {
        // Handle the single-pane phone case
        // Wrap the parcelable into a bundle and attach it to an Intent that will launch an PlayerActivity
        Bundle b = new Bundle();
        b.putParcelable(EXTRA_STEP, step);

        // Attach the Bundle to an intent
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(EXTRA_STEP, b);
        // Launch a new PlayerActivity
        startActivity(intent);
    }
}
