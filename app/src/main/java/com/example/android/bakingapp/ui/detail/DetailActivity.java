package com.example.android.bakingapp.ui.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.ActivityDetailBinding;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.player.PlayerActivity;
import com.example.android.bakingapp.ui.player.StepDetailFragment;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;
import static com.example.android.bakingapp.utilities.Constant.EXTRA_STEP_INDEX;

/**
 * The DetailActivity is responsible for displaying the ingredients and steps for a selected
 * {@link Recipe}.
 */
public class DetailActivity extends AppCompatActivity implements MasterListStepsFragment.OnStepClickListener {

    /** Member variable for the recipe */
    private Recipe mRecipe;

    /** This field is used for data binding **/
    private ActivityDetailBinding mDetailBinding;

    /** A single-pane display refers to phone screens, and two-pane to larger tablet screens */
    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        // Get the recipe data from the MainActivity
        mRecipe = getRecipeData();

        // Set the title for a selected recipe
        setTitle(mRecipe.getName());

        // Setup the UI
        setupUI();

        // Determine if you're creating a two-pane or single-pane display
        if (mDetailBinding.stepDetailContainer != null) {
            // This stepDetailContainer will only initially exist in the two-pane table case
            mTwoPane = true;

            if (savedInstanceState == null) {

                // Create a new StepDetailFragment
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                // Get the step 0
                Step step = mRecipe.getSteps().get(0);
                // Give the zeroth step and step index to the new fragment
                stepDetailFragment.setStep(step);
                stepDetailFragment.setStepIndex(0);

                // Add the fragment to its container using a FragmentManager and a Transaction
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_container, stepDetailFragment)
                        .commit();
            }

        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }
    }

    /**
     * This method is called from onCreate to setup UI
     */
    private void setupUI() {
        mRecipe = getRecipeData();
        int numIngredients = mRecipe.getIngredients().size();
        int numSteps = mRecipe.getSteps().size() - 1;

        // Give the TabLayout the ViewPager
        mDetailBinding.tabLayout.setupWithViewPager(mDetailBinding.viewpager);
        // Set gravity for the TabLayout
        mDetailBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Create an adapter that knows which fragment should be shown on each page
        DetailPagerAdapter detailPagerAdapter = new DetailPagerAdapter(this,
                getSupportFragmentManager(), numIngredients, numSteps);
        // Set the adapter onto the ViewPager
        mDetailBinding.viewpager.setAdapter(detailPagerAdapter);
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
        if (mTwoPane) {
            // Create two-pane interaction

            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            // Get the step
            Step step = mRecipe.getSteps().get(stepIndex);
            // Give the correct step and step index to the new fragment
            stepDetailFragment.setStep(step);
            stepDetailFragment.setStepIndex(stepIndex);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, stepDetailFragment)
                    .commit();

        } else {
            // Handle the single-pane phone case
            // Wrap the int and the parcelable into a bundle and attach it to an Intent that will
            // launch an PlayerActivity
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
}
