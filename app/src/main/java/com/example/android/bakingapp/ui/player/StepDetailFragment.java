package com.example.android.bakingapp.ui.player;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.FragmentStepDetailBinding;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import timber.log.Timber;

import static com.example.android.bakingapp.utilities.Constant.SAVE_STEP;

/**
 * The StepDetailFragment displays a selected recipe step that includes a video and step instruction.
 */
public class StepDetailFragment extends Fragment {

    /** This field is used for data binding */
    private FragmentStepDetailBinding mStepDetailBinding;

    /** Member variable for Step that this fragment displays */
    private Step mStep;

    /** Member variable for the ExoPlayer */
    private SimpleExoPlayer mExoPlayer;

    /** Initialize with 0 to start from the beginning of the window */
    private long mPlaybackPosition;

    /** Initialize with 0 to start from the beginning of the window */
    private int mCurrentWindow;

    private boolean mPlayWhenReady = true;

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

        // Load the saved state (the step) if there is one
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(SAVE_STEP);
        }

        // If the Step exists, set the description to the TextView
        // Otherwise, create a Log statement that indicates that the step was not found
        if(mStep != null) {
            String description = mStep.getDescription();
            // The Step 1 description of Brownies recipe contains a question mark, so replace it
            // with the degree sign.
            description = replaceString(description, rootView);
            mStepDetailBinding.tvDescription.setText(description);
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

    /**
     * Initialize ExoPlayer.
     */
    private void initializePlayer() {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer
            DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(getContext());
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    defaultRenderersFactory, trackSelector, loadControl);

            // Set the ExoPlayer to the playerView
            mStepDetailBinding.playerView.setPlayer(mExoPlayer);

            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
            mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        }
        // Prepare the MediaSource
        Uri mediaUri = Uri.parse(mStep.getVideoUrl());
        MediaSource mediaSource = buildMediaSource(mediaUri);
        mExoPlayer.prepare(mediaSource);
    }

    /**
     * Create a MediaSource
     *
     * @param mediaUri The URI of the sample to play.
     */
    private MediaSource buildMediaSource(Uri mediaUri) {
        String userAgent = Util.getUserAgent(this.getContext(), getString(R.string.app_name));
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(mediaUri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            // Initialize the player
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if (Util.SDK_INT <= Build.VERSION_CODES.M || mExoPlayer == null) {
            // Initialize the player
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mStepDetailBinding.playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /**
     * Replace a question mark "�" with the degree "°".
     * The Step 1 description of Brownies recipe contains a question mark, so replace it with the degree sign.
     */
    private String replaceString(String target, View v) {
        if (target.contains(v.getContext().getString(R.string.question_mark))) {
            target = target.replace(v.getContext().getString(R.string.question_mark),
                    v.getContext().getString(R.string.degree));
        }
        return target;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVE_STEP, mStep);
    }
}
