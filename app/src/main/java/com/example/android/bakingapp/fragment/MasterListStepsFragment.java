package com.example.android.bakingapp.fragment;

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
import com.example.android.bakingapp.adapter.StepsAdapter;
import com.example.android.bakingapp.databinding.FragmentMasterListStepsBinding;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;

public class MasterListStepsFragment extends Fragment {

    private Recipe mRecipe;

    private StepsAdapter mStepsAdapter;

    private FragmentMasterListStepsBinding mStepsBinding;

    // Mandatory empty constructor
    public MasterListStepsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Instantiate FragmentMasterListStepsBinding using DataBindingUtil
        mStepsBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_master_list_steps, container, false);

        View rootView = mStepsBinding.getRoot();

        mRecipe = getRecipeData();
        List<Step> steps = new ArrayList<>();

        mStepsAdapter = new StepsAdapter(steps);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mStepsBinding.rvSteps.setLayoutManager(layoutManager);
        mStepsBinding.rvSteps.setHasFixedSize(true);

        mStepsBinding.rvSteps.setAdapter(mStepsAdapter);

        mStepsAdapter.addAll(mRecipe.getSteps());

        // Display the number of steps
        setNumSteps();

        return rootView;
    }

    private Recipe getRecipeData() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_RECIPE)) {
                Bundle b = intent.getBundleExtra(EXTRA_RECIPE);
                mRecipe = b.getParcelable(EXTRA_RECIPE);
            }
        }
        return mRecipe;
    }

    /**
     * Displays the number of steps
     */
    private void setNumSteps() {
        // Exclude zero step
        int numSteps = mRecipe.getSteps().size() - 1;
        mStepsBinding.numSteps.setText(String.valueOf(numSteps));
    }
}
