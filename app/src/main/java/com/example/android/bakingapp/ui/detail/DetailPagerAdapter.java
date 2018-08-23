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

package com.example.android.bakingapp.ui.detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.utilities.Constant;

import static com.example.android.bakingapp.utilities.Constant.INGREDIENTS;
import static com.example.android.bakingapp.utilities.Constant.STEPS;

/**
 * The DetailPagerAdapter provides the appropriate {@link Fragment} for a view pager.
 */
public class DetailPagerAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context mContext;
    /** The number of ingredients */
    private int mNumIngredients;
    /** The number of steps */
    private int mNumSteps;

    /**
     * Creates a new {@link DetailPagerAdapter}
     *
     * @param context Context of the app
     * @param fm The fragment manager that will keep each fragment's state in the adapter across swipes
     */
    public DetailPagerAdapter(Context context, FragmentManager fm, int numIngredients, int numSteps) {
        super(fm);
        mContext = context;
        mNumIngredients = numIngredients;
        mNumSteps = numSteps;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case INGREDIENTS:
                return new MasterListIngredientsFragment();
            case STEPS:
                return new MasterListStepsFragment();
        }
        return null;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return Constant.PAGE_COUNT;
    }

    /**
     * Return a title string with the number of ingredients or steps to describe the specified page.
     *
     * @param position The position of the title requested
     * @return A title of the requested page
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case INGREDIENTS:
                String title = Constant.TAP_TITLE[position % Constant.PAGE_COUNT].toUpperCase();
                title += mContext.getString(R.string.space) +
                        mContext.getString(R.string.open_parenthesis) + mNumIngredients +
                        mContext.getString(R.string.close_parenthesis);
                return title;
            case STEPS:
                title = Constant.TAP_TITLE[position % Constant.PAGE_COUNT].toUpperCase();
                title += mContext.getString(R.string.space) +
                        mContext.getString(R.string.open_parenthesis) + mNumSteps +
                        mContext.getString(R.string.close_parenthesis);
                return title;
        }
        return null;

    }
}
