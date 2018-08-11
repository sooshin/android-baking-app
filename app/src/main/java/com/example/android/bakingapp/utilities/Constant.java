package com.example.android.bakingapp.utilities;

public final class Constant {

    private Constant() {
        // Restrict instantiation
    }

    /** The base baking URL */
    static final String BAKING_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    /** Extra for the recipe to be received in the intent */
    public static final String EXTRA_RECIPE = "recipe";

    /** Extra for the step to be received in the intent */
    public static final String EXTRA_STEP_INDEX = "step_index";

    /** Constant string for saving the current state of StepDetailFragment */
    public static final String SAVE_STEP = "save_step";
    public static final String STATE_STEP_INDEX = "state_step_index";
    public static final String STATE_PLAYBACK_POSITION = "state_playback_position";
    public static final String STATE_CURRENT_WINDOW = "state_current_window";
    public static final String STATE_PLAY_WHEN_READY = "state_play_when_ready";

    /** The notification channel ID is used to link notifications to this channel */
    public static final String BAKING_NOTIFICATION_CHANNEL_ID = "baking_notification_channel_id";
    /** The pending intent ID is used to uniquely reference the pending intent */
    public static final int BAKING_PENDING_INTENT_ID = 0;
    /** The notification ID is used to access our notification after we've displayed it */
    public static final int BAKING_NOTIFICATION_ID = 20;

    /** Constants for ExoPlayer */
    public static final float PLAYER_PLAYBACK_SPEED = 1f;
    public static final int REWIND_INCREMENT = 3000;
    public static final int FAST_FORWARD_INCREMENT = 3000;
    public static final int START_POSITION = 0;

    /** Database name */
    public static final String DATABASE_NAME = "recipe";

    /** The number of threads in the pool used in AppExecutors */
    public static final int NUMBER_OF_THREADS_THREE = 3;

    /** String array used to display the tap title */
    public static final String[] TAP_TITLE = new String[] {"Ingredients", "Steps"};
    /** Constant value for each fragment */
    public static final int INGREDIENTS = 0;
    public static final int STEPS = 1;
    /** The number of page */
    public static final int PAGE_COUNT = TAP_TITLE.length;

    /** Constant used in GridSpacingItemDecoration */
    public static final int ONE = 1;
}
