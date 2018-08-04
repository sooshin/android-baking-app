package com.example.android.bakingapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.android.bakingapp.utilities.Constant;

import timber.log.Timber;

/**
 * {@link RecipeDatabase} database for the application including a table for {@link RecipeEntry}
 * with the DAO {@link RecipeDao}
 */

// List of the entry class and associated TypeConverters
@Database(entities = {RecipeEntry.class}, version = 1, exportSchema = false)
@TypeConverters({IngredientsConverter.class, StepsConverter.class})
public abstract class RecipeDatabase extends RoomDatabase {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static RecipeDatabase sInstance;

    public static RecipeDatabase getInstance(Context context) {
        Timber.d("Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RecipeDatabase.class, Constant.DATABASE_NAME).build();
                Timber.d("Made new database");
            }
        }
        return sInstance;
    }

    // The associated DAOs for the database
    public abstract RecipeDao recipeDao();
}
