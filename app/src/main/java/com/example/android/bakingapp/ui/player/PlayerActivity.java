package com.example.android.bakingapp.ui.player;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.ActivityPlayerBinding;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;
import static com.example.android.bakingapp.utilities.Constant.EXTRA_STEP_INDEX;

/**
 * This activity will display a recipe step that includes a video and step instruction.
 */
public class PlayerActivity extends AppCompatActivity {

    /** Member variable for the Step */
    private Step mStep;

    /** Member variable for the Recipe */
    private Recipe mRecipe;

    /** This field is used for data binding **/
    private ActivityPlayerBinding mPlayerBinding;

    /** Variable to store the index of the step that this activity displays */
    private int mStepIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerBinding = DataBindingUtil.setContentView(this, R.layout.activity_player);

        // Only create a new fragment when there is no previously saved state
        if (savedInstanceState == null) {

            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(EXTRA_STEP_INDEX)) {
                    // Get the correct step index from the intent
                    Bundle b = intent.getBundleExtra(EXTRA_STEP_INDEX);
                    mStepIndex = b.getInt(EXTRA_STEP_INDEX);
                }
                if (intent.hasExtra(EXTRA_RECIPE)) {
                    // Get the recipe from the intent
                    Bundle b = intent.getBundleExtra(EXTRA_RECIPE);
                    mRecipe = b.getParcelable(EXTRA_RECIPE);
                }
            }

            // Create a new StepDetailFragment
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            // Set the step
            mStep = mRecipe.getSteps().get(mStepIndex);
            stepDetailFragment.setStep(mStep);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }
    }

    public void onPreviousClick(View view) {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        if (mStepIndex > 0) {
            mStepIndex--;
            stepDetailFragment.setStep(mRecipe.getSteps().get(mStepIndex));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_container, stepDetailFragment)
                    .commit();

        } else {
            Toast.makeText(this, "This is the first page", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNextClick(View view) {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        if (mStepIndex < mRecipe.getSteps().size() - 1) {
            mStepIndex++;
            stepDetailFragment.setStep(mRecipe.getSteps().get(mStepIndex));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_container, stepDetailFragment)
                    .commit();

        } else {
            Toast.makeText(this, "This is the last page", Toast.LENGTH_SHORT).show();
        }
    }
}
