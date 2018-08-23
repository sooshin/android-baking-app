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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.detail.DetailActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;
import static com.example.android.bakingapp.utilities.Constant.POSITION_ONE;
import static com.example.android.bakingapp.utilities.Constant.POSITION_TWO;
import static com.example.android.bakingapp.utilities.Constant.POSITION_ZERO;
import static com.example.android.bakingapp.utilities.Constant.RECIPE_NAME_AT_ZERO;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * This test demos a user clicking the add ingredient button and the step item inside RecyclerView.
 */
@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    private static final double QUANTITY = 2.0;
    private static final String MEASURE = "CUP";
    private static final String INGREDIENT = "Graham Cracker crumbs";

    private static final int STEP_ID = 0;
    private static final String STEP_SHORT_DESCRIPTION = "Recipe Introduction";
    private static final String STEP_DESCRIPTION = "Recipe Introduction";
    private static final String STEP_VIDEO_URL = "";
    private static final String STEP_THUMBNAIL_URL = "";

    private static final int RECIPE_ID = 0;
    private static final int RECIPE_SERVINGS = 8;
    private static final String RECIPE_IMAGE = "";

    private static final String CLASSNAME_CONSTRAINT = "android.support.constraint.ConstraintLayout";
    private static final String CLASSNAME_LINEAR = "android.widget.LinearLayout";

    @Rule
    public ActivityTestRule<DetailActivity> mActivityTestRule
            = new ActivityTestRule<DetailActivity>(DetailActivity.class) {

        /**
         * Add an intent with extras to launch the activity for each test.
         * Reference @see: "https://discussions.udacity.com/t/how-to-initialize-a-stepactivity-
         * with-data-for-testing/281414/3"
         */
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

            Recipe recipe = new Recipe(RECIPE_ID, RECIPE_NAME_AT_ZERO,
                    getIngredientsForTest(), getStepsForTest(), RECIPE_SERVINGS, RECIPE_IMAGE);

            Bundle b = new Bundle();
            b.putParcelable(EXTRA_RECIPE, recipe);

            Intent result = new Intent(targetContext, DetailActivity.class);
            result.putExtra(EXTRA_RECIPE, b);
            return result;
        }
    };

    /**
     * Clicks on a add ingredient button.
     */
    @Test
    public void clickAddIngredientButton() {
        // Scroll to the position that needs to be matched
        onView(withId(R.id.rv_ingredients))
                .perform(RecyclerViewActions.scrollToPosition(POSITION_ZERO));

        // Click on add button
        onView(withId(R.id.iv_add)).perform(click());
    }

    /**
     * Clicks on a RecyclerView item and checks it opens up the PlayerActivity.
     */
    @Test
    public void clickRecyclerViewItem_OpensPlayerActivity() {
        // Clicks the tap of the steps in the tap layout
        onView(allOf(childAtPosition(
                childAtPosition(
                        withId(R.id.tab_layout), POSITION_ZERO), POSITION_ONE), isDisplayed()))
                .perform(click());

        // Clicks on a RecyclerView item
        onView(allOf(withId(R.id.rv_steps),
                childAtPosition(
                        withClassName(is(CLASSNAME_CONSTRAINT)), POSITION_ZERO)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ZERO,click()));

        // Checks that the PlayerActivity opens with player view
        onView(allOf(withId(R.id.player_view),
                childAtPosition(
                        childAtPosition(
                                withClassName(is(CLASSNAME_LINEAR)),
                                POSITION_ZERO), POSITION_TWO),
                isDisplayed()));

    }

    /**
     * Returns the list of ingredients
     */
    private List<Ingredient> getIngredientsForTest() {
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient(QUANTITY, MEASURE, INGREDIENT);
        ingredients.add(ingredient);
        return ingredients;
    }

    /**
     * Returns the list of steps
     */
    private List<Step> getStepsForTest() {
        List<Step> steps = new ArrayList<>();
        //public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        Step step = new Step(STEP_ID, STEP_SHORT_DESCRIPTION,
                STEP_DESCRIPTION, STEP_VIDEO_URL, STEP_THUMBNAIL_URL);
        steps.add(step);
        return steps;
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
