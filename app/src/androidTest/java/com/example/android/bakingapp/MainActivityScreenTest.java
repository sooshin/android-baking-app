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

package com.example.android.bakingapp;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingapp.ui.main.MainActivity;
import com.example.android.bakingapp.utilities.OkHttpProvider;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.android.bakingapp.utilities.Constant.NAME_OKHTTP;
import static com.example.android.bakingapp.utilities.Constant.POSITION_ONE;
import static com.example.android.bakingapp.utilities.Constant.RECIPE_NAME_AT_ONE;
import static org.hamcrest.core.Is.is;

/**
 * This test demos a user clicking on a RecyclerView item in MainActivity which opens up the
 * corresponding DetailActivity.
 *
 * Reference: @see "https://github.com/chiuki/espresso-samples/tree/master/toolbar-title"
 * "http://blog.sqisland.com/2015/05/espresso-match-toolbar-title.html"
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    // Register the IdlingResource for OkHttp before the test
    @Before
    public void registerIdlingResource() {
        IdlingResource idlingResource = OkHttp3IdlingResource.create(NAME_OKHTTP,
                OkHttpProvider.getOkHttpInstance());
        IdlingRegistry.getInstance().register(idlingResource);
    }

    /**
     * Clicks on a RecyclerView item and checks it opens up the DetailActivity with the correct
     * toolbar title.
     */
    @Test
    public void clickRecyclerViewItem_OpensDetailActivity() {

        // Check that the toolbar title in the MainActivity matches app name
        CharSequence title = InstrumentationRegistry.getTargetContext().getString(R.string.app_name);
        matchToolbarTitle(title);

        // Clicks on a specific RecyclerView item
        onView(withId(R.id.rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ONE, click()));

        // Checks that the DetailActivity opens with the correct title displayed
        matchToolbarTitle(RECIPE_NAME_AT_ONE);

    }

    /**
     * Looks for the Toolbar itself, and checks that it has the expected title.
     */
    private static void matchToolbarTitle(CharSequence title) {
        onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
    }

    /**
     * Returns a custom BoundedMatcher, which gives us type safety.
     */
    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {

            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                // Checks the value fo the title by calling Toolbar.getTitle()
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    // Unregister resources when not needed to avoid malfunction
    @After
    public void unregisterIdlingResource() {
        IdlingResource idlingResource = OkHttp3IdlingResource.create(NAME_OKHTTP,
                OkHttpProvider.getOkHttpInstance());
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
