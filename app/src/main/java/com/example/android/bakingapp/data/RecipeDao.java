package com.example.android.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * {@link Dao} which provides an api for all data operations with the {@link RecipeDatabase}
 */
@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    LiveData<List<RecipeEntry>> getAllRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RecipeEntry> recipeEntries);
}
