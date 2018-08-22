package com.example.android.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.ui.main.MainActivity;
import com.example.android.bakingapp.utilities.OkHttpProvider;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.bakingapp.utilities.Constant.NAME_OKHTTP;
import static com.example.android.bakingapp.utilities.Constant.POSITION_ZERO;
import static com.example.android.bakingapp.utilities.Constant.RECIPE_NAME_AT_ZERO;

/**
 * This test checks that MainActivity opens with the correct recipe name inside a RecyclerView item.
 *
 * Reference: @see "https://github.com/googlesamples/android-testing/tree/master/ui/espresso/RecyclerViewSample"
 * @see "https://github.com/chiuki/espresso-samples/tree/master/idling-resource-okhttp"
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    // Register the IdlingResource for OkHttp before the test
    @Before
    public void registerIdlingResource() {
        IdlingResource idlingResource = OkHttp3IdlingResource.create(NAME_OKHTTP,
                OkHttpProvider.getOkHttpInstance());
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void scrollToPosition_CheckRecipeName() {
        // Scroll to the position that needs to be matched
        onView(withId(R.id.rv))
                .perform(RecyclerViewActions.scrollToPosition(POSITION_ZERO));

        // Match the recipe name and check that the correct recipe name is displayed
        onView(withText(RECIPE_NAME_AT_ZERO)).check(matches(isDisplayed()));
    }

    // Unregister resources when not needed to avoid malfunction
    @After
    public void unregisterIdlingResource() {
        IdlingResource idlingResource = OkHttp3IdlingResource.create(NAME_OKHTTP,
                OkHttpProvider.getOkHttpInstance());
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
