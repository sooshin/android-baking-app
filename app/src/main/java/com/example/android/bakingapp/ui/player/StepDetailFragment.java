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
import com.google.android.exoplayer2.C;
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
import static com.example.android.bakingapp.utilities.Constant.STATE_CURRENT_WINDOW;
import static com.example.android.bakingapp.utilities.Constant.STATE_PLAYBACK_POSITION;
import static com.example.android.bakingapp.utilities.Constant.STATE_PLAY_WHEN_READY;

/**
 * The StepDetailFragment displays a selected recipe step that includes a video and step instruction.
 *
 * Reference: @see "https://codelabs.developers.google.com/codelabs/exoplayer-intro"
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

    /** Initialize with 0 to start from the first item in the TimeLine */
    private int mCurrentWindow;

    private boolean mPlayWhenReady;

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

        // Load the saved state (the step, playback position, current window, play when ready) if there is one
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(SAVE_STEP);
            mPlaybackPosition = savedInstanceState.getLong(STATE_PLAYBACK_POSITION);
            mCurrentWindow = savedInstanceState.getInt(STATE_CURRENT_WINDOW);
            mPlayWhenReady = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY);
        } else {
            // Clear the start position
            mCurrentWindow = C.INDEX_UNSET;
            mPlaybackPosition = C.TIME_UNSET;
            mPlayWhenReady = true;
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
        }
        // Prepare the MediaSource
        Uri mediaUri = Uri.parse(mStep.getVideoUrl());
        MediaSource mediaSource = buildMediaSource(mediaUri);

        // Restore the playback position
        boolean haveStartPosition = mCurrentWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        }
        // The boolean flags indicate whether to reset position and state of the player
        mExoPlayer.prepare(mediaSource, !haveStartPosition, false);
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
        // Starting with API level 24 Android supports multiple windows. As our app can be visible
        // but not active in split window mode, we need to initialize the player in onStart().
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            // Initialize the player
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        // Before API level 24 we wait as long as possible until we grab resources, so we wait until
        // onResume() before initializing the player.
        if (Util.SDK_INT <= Build.VERSION_CODES.M || mExoPlayer == null) {
            // Initialize the player
            initializePlayer();
        }
    }

    /**
     * Enables the user to have a pure full screen experience
     */
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
        // Before API level 24 there is no guarantee of onStop() being called. So we have to release
        // the player as early as possible in onPause().
        if (Util.SDK_INT <= Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Starting with API level 24 onStop() is guaranteed to be called and in the paused mode
        // our activity is eventually still visible. Hence we need to wait releasing until onStop().
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            updateCurrentPosition();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /**
     * Updates the current state of the player
     */
    private void updateCurrentPosition() {
        if (mExoPlayer != null) {
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
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
        // Update the current state of the player
        updateCurrentPosition();
        // Store the playback position to our bundle
        outState.putLong(STATE_PLAYBACK_POSITION, mPlaybackPosition);
        outState.putInt(STATE_CURRENT_WINDOW, mCurrentWindow);
        outState.putBoolean(STATE_PLAY_WHEN_READY, mPlayWhenReady);
    }
}
