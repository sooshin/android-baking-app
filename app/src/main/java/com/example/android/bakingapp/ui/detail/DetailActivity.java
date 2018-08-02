package com.example.android.bakingapp.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.player.PlayerActivity;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;
import static com.example.android.bakingapp.utilities.Constant.EXTRA_STEP_INDEX;

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
     * @param stepIndex Position of the step in the list
     */
    @Override
    public void onStepSelected(int stepIndex) {
        // Handle the single-pane phone case
        // Wrap the int and the parcelable into a bundle and attach it to an Intent that will launch an PlayerActivity
        Bundle b = new Bundle();
        b.putInt(EXTRA_STEP_INDEX, stepIndex);
        b.putParcelable(EXTRA_RECIPE, mRecipe);

        // Attach the Bundle to an intent
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(EXTRA_STEP_INDEX, b);
        intent.putExtra(EXTRA_RECIPE, b);
        // Launch a new PlayerActivity
        startActivity(intent);
    }
}
