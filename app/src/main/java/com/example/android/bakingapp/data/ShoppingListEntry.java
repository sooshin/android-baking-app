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

