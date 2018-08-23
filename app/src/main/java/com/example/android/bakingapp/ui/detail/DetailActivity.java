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

package com.example.android.bakingapp.ui.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.ActivityDetailBinding;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.player.PlayerActivity;
import com.example.android.bakingapp.ui.player.StepDetailFragment;
import com.example.android.bakingapp.ui.shopping.ShoppingActivity;
import com.squareup.picasso.Picasso;

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

        // Determine if you're creating a two-pane or single-pane display
        if (mDetailBinding.stepDetailContainer != null) {
            // This stepDetailContainer will only initially exist in the two-pane tablet case
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

            // Setup TabLayout with ViewPager only in the single-pane mode
            setupUI();
            // Display the number of servings
            setNumServings();
            //
            setCollapsingToolbarTextColor();
        }

        // Show the up button in the actionbar
        showUpButton(mTwoPane);
    }

    /**
     * This method is called from onCreate to setup UI in single-pane mode
     */
    private void setupUI() {
        // Get the number of ingredients and steps
        int numIngredients = mRecipe.getIngredients().size();
        int numSteps = mRecipe.getSteps().size() - 1;

        // Display the image of recipe
        displayImage();

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
     * Check if the image of recipe exists. If so, load the image with Picasso
     * otherwise displays baking_ingredients image.
     */
    private void displayImage() {
        String imageUrl = mRecipe.getImage();
        if (imageUrl.isEmpty()) {
            mDetailBinding.ivDetail.setImageResource(R.drawable.baking_ingredients);
        } else {
            Picasso.with(this)
                    .load(imageUrl)
                    .error(R.drawable.baking_ingredients)
                    .placeholder(R.drawable.baking_ingredients)
                    .into(mDetailBinding.ivDetail);
        }
    }

    /**
     * Display the number of servings
     */
    private void setNumServings() {
        int numServings = mRecipe.getServings();
        mDetailBinding.tvServings.setText(String.valueOf(numServings));
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

    /**
     * Show the up button in Collapsing Toolbar(single-pane) or in the action bar(two-pane)
     *
     * @param twoPane A single-pane display refers to phone screens, and two-pane to larger
     *                tablet screens
     */
    private void showUpButton(boolean twoPane) {
        if (!twoPane) {
            setSupportActionBar(mDetailBinding.toolbar);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * When the arrow icon in collapsing toolbar (single-pane) is clicked, finishes DetailActivity.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.home:
                onBackPressed();
                return true;
            case R.id.action_shopping:
                // Launch ShoppingActivity to display shopping list
                startShoppingActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * When the user clicks shopping cart icon in the menu, launch ShoppingActivity to display
     * shopping list.
     */
    private void startShoppingActivity() {
        Intent intent = new Intent(this, ShoppingActivity.class);
        startActivity(intent);
    }

    /**
     * Sets the text color to white for the expanded title and sets the text color to primary dark
     * for the collapsed title.
     *
     * Reference: @see "https://stackoverflow.com/questions/43874025/toolbar-title-text-color"
     */
    private void setCollapsingToolbarTextColor() {
        mDetailBinding.collapsingToolbarLayout.setExpandedTitleColor(
                getResources().getColor(R.color.white));
        mDetailBinding.collapsingToolbarLayout.setCollapsedTitleTextColor(
                getResources().getColor(R.color.colorPrimaryDark));
    }

}
