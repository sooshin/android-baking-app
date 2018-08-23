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

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.FragmentMasterListStepsBinding;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;

/**
 * The MasterListStepsFragment displays the list of steps for the recipe.
 */
public class MasterListStepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {

    /** Member variable for the recipe */
    private Recipe mRecipe;

    /** Member variable for StepsAdapter */
    private StepsAdapter mStepsAdapter;

    /** This field is used for data binding */
    private FragmentMasterListStepsBinding mStepsBinding;

    /**
     * Define a new interface OnStepClickListener that triggers a callback in the host activity,
     * DetailActivity.
     */
    OnStepClickListener mCallback;

    /** OnStepClickListener interface, calls a method in the host activity named onStepSelected */
    public interface OnStepClickListener {
        void onStepSelected(int stepIndex);
    }


    /**
     * Mandatory empty constructor
     */
    public MasterListStepsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Instantiate FragmentMasterListStepsBinding using DataBindingUtil
        mStepsBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_master_list_steps, container, false);

        View rootView = mStepsBinding.getRoot();

        // Get the recipe data from the MainActivity
        mRecipe = getRecipeData();

        // Display the number of steps (excluding 0 step) in the two-pane tablet case
        setNumIngredientsTwoPane(mRecipe);

        // Initialize a StepsAdapter
        initAdapter();

        return rootView;
    }

    /**
     * Create a StepsAdapter and set it to the RecyclerView
     */
    private void initAdapter() {
        // Create an empty ArrayList
        List<Step> steps = new ArrayList<>();

        // The StepsAdapter is responsible for displaying each step in the list
        mStepsAdapter = new StepsAdapter(steps, this);

        // A LinearLayoutManager is responsible for measuring and positioning item views within a
        // RecyclerView into a linear list.
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // Set the layout manager to the RecyclerView
        mStepsBinding.rvSteps.setLayoutManager(layoutManager);
        // Use this setting to improve performance if you know that changes in content do not
        // change the child layout size in the RecyclerView
        mStepsBinding.rvSteps.setHasFixedSize(true);

        // Set the StepsAdapter to the RecyclerView
        mStepsBinding.rvSteps.setAdapter(mStepsAdapter);

        // Add a list of steps to the StepsAdapter
        mStepsAdapter.addAll(mRecipe.getSteps());
    }

    /**
     * Display the number of steps in the two-pane tablet case
     */
    private void setNumIngredientsTwoPane(Recipe recipe) {
        // The TextView tvStepsLabel will only initially exist in the two-pane tablet case
        if (mStepsBinding.tvStepsLabel != null) {
            // The number of steps excludes 0 step.
            String numSteps = getString(R.string.steps_label) +
                    getString(R.string.space) + getString(R.string.open_parenthesis)
                    + (recipe.getSteps().size() - 1) + getString(R.string.close_parenthesis);
            mStepsBinding.tvStepsLabel.setText(numSteps);
        }
    }

    /**
     * Gets recipe data from the MainActivity
     *
     * @return The Recipe data
     */
    private Recipe getRecipeData() {
        Intent intent = getActivity().getIntent();
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
     * Override onAttach to make sure that the container activity has implemented the callback
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    /**
     * Handles RecyclerView item clicks
     *
     * @param stepIndex Position of the step in the list
     */
    @Override
    public void onItemClick(int stepIndex) {
        // Trigger the callback method and pass in the step index that was clicked
        mCallback.onStepSelected(stepIndex);
    }
}
