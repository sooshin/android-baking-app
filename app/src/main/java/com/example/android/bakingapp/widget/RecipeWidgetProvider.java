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

package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.detail.DetailActivity;
import com.example.android.bakingapp.ui.main.MainActivity;
import com.example.android.bakingapp.utilities.BakingUtils;

import java.util.List;

import static com.example.android.bakingapp.utilities.Constant.DEFAULT_INTEGER;
import static com.example.android.bakingapp.utilities.Constant.DEFAULT_INTEGER_FOR_SERVINGS;
import static com.example.android.bakingapp.utilities.Constant.DEFAULT_STRING;
import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;
import static com.example.android.bakingapp.utilities.Constant.WIDGET_PENDING_INTENT_ID;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = getIngredientListRemoteView(context, appWidgetId);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Update the recipe name in the app widget.
     */
    static void updateAppWidgetTitle(Context context, AppWidgetManager appWidgetManager,
                                     int appWidgetId) {
        RemoteViews views = getTitleRemoteView(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the TextView widget
     *
     * @param context The context of the app
     * @return The RemoteViews for displaying recipe name
     */
    private static RemoteViews getTitleRemoteView(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Get the recipe name used for displaying in the app widget
        String recipeName = sharedPreferences.getString(
                context.getString(R.string.pref_recipe_name_key), DEFAULT_STRING);

        // Extract recipe data used for creating the recipe object
        int recipeId = sharedPreferences.getInt(
                context.getString(R.string.pref_recipe_id_key), DEFAULT_INTEGER);
        String ingredientString = sharedPreferences.getString(
                context.getString(R.string.pref_ingredient_list_key),  DEFAULT_STRING);
        String stepString = sharedPreferences.getString(
                context.getString(R.string.pref_step_list_key),  DEFAULT_STRING);
        int servings =  sharedPreferences.getInt(
                context.getString(R.string.pref_servings_key), DEFAULT_INTEGER_FOR_SERVINGS);
        String image = sharedPreferences.getString(
                context.getString(R.string.pref_image_key),  DEFAULT_STRING);

        // Create an Intent to launch MainActivity or DetailActivity when clicked
        Intent intent;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        // If a user does not click the recipe item, there is no data to launch the DetailActivity.
        // In this case, launch the MainActivity. Otherwise, launch the DetailActivity.
        if (ingredientString.isEmpty() || stepString.isEmpty()) {
            intent = new Intent(context, MainActivity.class);
            // Display the app name in the app widget
            views.setTextViewText(R.id.widget_recipe_name, context.getString(R.string.app_name));
        } else {
            intent = new Intent(context, DetailActivity.class);

            // Convert ingredient string to the list of ingredients
            List<Ingredient> ingredientList = BakingUtils.toIngredientList(ingredientString);
            // Convert step string to the list of steps
            List<Step> stepList = BakingUtils.toStepList(stepString);

            // Create the recipe that a user clicks
            Recipe recipe = new Recipe(recipeId, recipeName, ingredientList, stepList, servings, image);

            // Pass the bundle through Intent
            Bundle b = new Bundle();
            b.putParcelable(EXTRA_RECIPE, recipe);
            intent.putExtra(EXTRA_RECIPE, b);

            // Display the recipe name in the app widget
            views.setTextViewText(R.id.widget_recipe_name, recipeName);
        }

        // Widgets allow click handlers to only launch pending intents
        PendingIntent pendingIntent = PendingIntent.getActivity(context, WIDGET_PENDING_INTENT_ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_recipe_name, pendingIntent);

        return views;
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the ListView mode widget
     *
     * @param context The context
     * @return The RemoteViews for the ListView mode widget
     */
    private static RemoteViews getIngredientListRemoteView(Context context, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        // Set up the intent that starts the ListWidgetService, which will provide the views for
        // this collection
        Intent intent = new Intent(context, ListWidgetService.class);
        // Add the app widget Id to the intent extras
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        // Handle empty view
        views.setEmptyView(R.id.widget_list_view, R.id.widget_empty_view);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            // Update the recipe name in the app widget
            updateAppWidgetTitle(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
            // Trigger data update to handle the ListView widgets and force a data refresh
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

