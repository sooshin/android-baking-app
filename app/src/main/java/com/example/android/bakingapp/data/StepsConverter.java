package com.example.android.bakingapp.data;

import android.arch.persistence.room.TypeConverter;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * {@link TypeConverter} for string to list of {@link Step}s
 * <p>
 * This stores the list of steps as a string in the database, but returns it as a list of
 * {@link Step}s
 *
 * Reference: @see "https://stackoverflow.com/questions/44580702/android-room-persistent-library
 * -how-to-insert-class-that-has-a-list-object-fie"
 * "https://medium.com/@toddcookevt/android-room-storing-lists-of-objects-766cca57e3f9"
 */
public class StepsConverter {

    @TypeConverter
    public static List<Step> toStepList(String stepString) {
        if (stepString == null) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Step>>() {}.getType();

        return gson.fromJson(stepString, listType);
    }

    @TypeConverter
    public static String toStepString(List<Step> stepList) {
        if (stepList == null) {
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();

        return gson.toJson(stepList, listType);
    }
}
