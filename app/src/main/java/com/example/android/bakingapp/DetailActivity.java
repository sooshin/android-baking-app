package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.fragment.MasterListStepsFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;

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

    @Override
    public void onStepSelected(Step step) {
        // Handle the single-pane phone case
        Toast.makeText(this, "stepId: " + step.getStepId(), Toast.LENGTH_SHORT).show();
    }
}
