package com.example.android.bakingapp.data;

import android.arch.persistence.room.TypeConverter;

import com.example.android.bakingapp.model.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * {@link TypeConverter} for string to list of {@link Ingredient}s
 * <p>
 * This stores the list of ingredients as a string in the database, but returns it as a list of
 * {@link Ingredient}s
 *
 * Reference: @see "https://stackoverflow.com/questions/44580702/android-room-persistent-library
 * -how-to-insert-class-that-has-a-list-object-fie"
 * "https://medium.com/@toddcookevt/android-room-storing-lists-of-objects-766cca57e3f9"
 */
public class IngredientsConverter {

    @TypeConverter
    public static List<Ingredient> toIngredientList(String ingredientString) {
        if (ingredientString == null) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();

        return gson.fromJson(ingredientString, listType);
    }

    @TypeConverter
    public static String toIngredientString(List<Ingredient> ingredientList) {
        if (ingredientList == null) {
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();

        return gson.toJson(ingredientList, listType);
    }

}
