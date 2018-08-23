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

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Defines the schema of a table in Room for a single shopping list item.
 *
 */
@Entity(tableName = "shopping_list")
public class ShoppingListEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "recipe_name")
    private String recipeName;

    private double quantity;

    private String measure;

    private String ingredient;

    @ColumnInfo(name = "ingredient_index")
    private int index;

    /**
     * Constructor
     *
     * @param recipeName Name of recipe
     * @param quantity Quantity of ingredient
     * @param measure Measure of ingredient
     * @param ingredient Ingredient of the recipe
     */
    @Ignore
    public ShoppingListEntry(String recipeName, double quantity, String measure, String ingredient, int index) {
        this.recipeName = recipeName;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.index = index;
    }

    /**
     * Constructor used by Room to create ShoppingListEntries
     */
    public ShoppingListEntry(int id, String recipeName, double quantity, String measure, String ingredient, int index) {
        this.id = id;
        this.recipeName = recipeName;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getIndex() {
        return index;
    }
}

