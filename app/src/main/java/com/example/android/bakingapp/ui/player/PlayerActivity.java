package com.example.android.bakingapp.ui.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.ui.player.StepDetailFragment;
import com.example.android.bakingapp.model.Step;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_STEP;

/**
 * This activity will display a recipe step that includes a video and step instruction.
 */
public class PlayerActivity extends AppCompatActivity {

    /** Member variable for the Step */
    private Step mStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Only create a new fragment when there is no previously saved state
        if (savedInstanceState == null) {

            // Get the correct step from the intent
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(EXTRA_STEP)) {
                    Bundle b = intent.getBundleExtra(EXTRA_STEP);
                    mStep = b.getParcelable(EXTRA_STEP);
                }
            }

            // Create a new StepDetailFragment
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            // Set the step
            stepDetailFragment.setStep(mStep);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }
    }
}
