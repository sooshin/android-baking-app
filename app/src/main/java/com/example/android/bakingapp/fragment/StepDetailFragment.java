package com.example.android.bakingapp.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.FragmentStepDetailBinding;
import com.example.android.bakingapp.model.Step;

import timber.log.Timber;

/**
 * The StepDetailFragment displays a selected recipe step that includes a video and step instruction.
 */
public class StepDetailFragment extends Fragment {

    /** This field is used for data binding */
    private FragmentStepDetailBinding mStepDetailBinding;

    /** Member variable for Step that this fragment displays */
    private Step mStep;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Instantiate FragmentStepDetailBinding using DataBindingUtil
        mStepDetailBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_step_detail, container, false);
        View rootView = mStepDetailBinding.getRoot();

        // If the Step exists, set the description to the TextView
        // Otherwise, create a Log statement that indicates that the step was not found
        if(mStep != null) {
            mStepDetailBinding.tvDescription.setText(mStep.getDescription());
        } else {
            Timber.v("This fragment has a null step");
        }

        // Return the rootView
        return rootView;
    }

    /**
     * Setter method for displaying current step
     */
    public void setStep(Step step) {
        mStep = step;
    }
}
