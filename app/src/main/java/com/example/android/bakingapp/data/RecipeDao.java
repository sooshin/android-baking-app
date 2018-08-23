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

package com.example.android.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * {@link Dao} which provides an api for all data operations with the {@link RecipeDatabase}
 */
@Dao
public interface RecipeDao {

    // ShoppingListEntry

    /**
     * Select all {@link ShoppingListEntry} entries
     *
     * @return {@link LiveData} with the list of shopping list entries
     */
    @Query("SELECT * FROM shopping_list")
    LiveData<List<ShoppingListEntry>> getAllShoppingList();

    /**
     * Insert a {@link ShoppingListEntry} into shopping_list table
     *
     * @param shoppingListEntry The shopping list entry the user wants to add into the shopping list
     */
    @Insert
    void insertIngredient(ShoppingListEntry shoppingListEntry);

    /**
     * Delete a {@link ShoppingListEntry} from shopping_list table
     *
     * @param shoppingListEntry The shopping list entry the user wants to delete from the shopping list
     */
    @Delete
    void deleteIngredient(ShoppingListEntry shoppingListEntry);

    /**
     * Select ingredient_index where the value in column recipe_name is the given recipe name
     *
     * @param recipeName The recipe name
     * @return {@link LiveData} with the list of ingredient indices
     */
    @Query("SELECT ingredient_index FROM shopping_list WHERE recipe_name = :recipeName")
    LiveData<List<Integer>> getIndices(String recipeName);
}
