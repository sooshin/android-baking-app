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

    /** Constant for the column width in the GridAutofitLayoutManager */
    public static final int GRID_COLUMN_WIDTH = 520;
    /** Default value for column width in the GridAutofitLayoutManager */
    public static final int GRID_COLUMN_WIDTH_DEFAULT = 48;
    /** Initial span count for the GridAutofitLayoutManager */
    public static final int GRID_SPAN_COUNT = 1;

    /** Constants for position number */
    public static final int POSITION_ZERO = 0;
    public static final int POSITION_ONE = 1;
    public static final int POSITION_TWO = 2;
    static final int POSITION_THREE = 3;
    static final int NUM_POSITION_FOUR = 4;

    /** Constant for setting padding (px) of the recipe image */
    public static final int RECIPE_IMAGE_PADDING = 140;

    /** Used for building OkHttp client */
    static final int CONNECT_TIMEOUT_TEN = 10;
    static final int READ_TIMEOUT_TWENTY = 20;

    /** The name used for OkHttp IdlingResource */
    public static final String NAME_OKHTTP= "OkHttp";

    /** Constants used for MainActivityBasicTest */
    public static final String RECIPE_NAME_AT_ZERO = "Nutella Pie";
    public static final String RECIPE_NAME_AT_ONE = "Brownies";

    /** The default value for the SharedPreferences */
    public static final String DEFAULT_STRING = "";
    public static final int DEFAULT_INTEGER = 1;
    public static final int DEFAULT_INTEGER_FOR_SERVINGS = 8;

    /** The pending intent ID is used for the widget to uniquely reference the pending intent */
    public static final int WIDGET_PENDING_INTENT_ID = 0;
}
