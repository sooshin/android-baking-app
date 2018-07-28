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
    public static final String EXTRA_STEP = "step";
}
