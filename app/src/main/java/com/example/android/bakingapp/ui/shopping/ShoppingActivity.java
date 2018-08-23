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

package com.example.android.bakingapp.ui.shopping;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import com.example.android.bakingapp.AppExecutors;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeDatabase;
import com.example.android.bakingapp.data.ShoppingListEntry;
import com.example.android.bakingapp.databinding.ActivityShoppingBinding;
import com.example.android.bakingapp.utilities.InjectorUtils;

import java.util.List;

/**
 * This activity will display the shopping list that a user clicks to add to the shopping list.
 */
public class ShoppingActivity extends AppCompatActivity {

    /** Member variable for the RecipeDatabase */
    private RecipeDatabase mDb;

    /** Member variable for ShoppingAdapter */
    private ShoppingAdapter mShoppingAdapter;

    /** This field is used for data binding */
    private ActivityShoppingBinding mShoppingBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShoppingBinding = DataBindingUtil.setContentView(this, R.layout.activity_shopping);

        // Initialize the ShoppingAdapter
        initAdapter();

        // Get the RecipeDatabase instance
        mDb = RecipeDatabase.getInstance(getApplicationContext());

        // Setup shopping view model
        setupViewModel();

        // Setup Item touch helper to recognize when a user swipes to delete an item
        setupItemTouchHelper();

        // Display the up button in the actionbar
        showUpButton();
    }

    /**
     * Creates a LayoutManager and ShoppingAdapter and set them to the RecyclerView
     */
    private void initAdapter() {
        // Set the layout manager to the RecyclerView
        mShoppingBinding.rvShopping.setLayoutManager(new LinearLayoutManager(this));
        mShoppingBinding.rvShopping.setHasFixedSize(true);
        // The ShoppingAdapter is responsible for displaying each shopping list item in the list.
        mShoppingAdapter = new ShoppingAdapter(this);
        // // Set adapter to the RecyclerView
        mShoppingBinding.rvShopping.setAdapter(mShoppingAdapter);
    }

    /**
     * Every time the user data is updated, update UI.
     */
    private void setupViewModel() {
        // Get the ViewModel from the factory
        ShoppingViewModelFactory factory = InjectorUtils.provideListViewModelFactory(this);
        ShoppingViewModel shoppingViewModel = ViewModelProviders.of(this, factory).get(ShoppingViewModel.class);

        // Update the list of ShoppingListEntries
        shoppingViewModel.getList().observe(this, new Observer<List<ShoppingListEntry>>() {
            @Override
            public void onChanged(@Nullable List<ShoppingListEntry> shoppingListEntries) {
                mShoppingAdapter.setShoppingList(shoppingListEntries);

                // When the shopping list is empty, show an empty view, otherwise, make the
                // shopping list view visible.
                if (shoppingListEntries == null || shoppingListEntries.size() == 0) {
                    showEmptyView();
                } else {
                    showShoppingView();
                }
            }
        });
    }

    /**
     *  Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
     *  An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     *  and uses callbacks to signal when a user is performing these actions.
     */
    private void setupItemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // Delete shoppingListEntry at the adapter position
                        int adapterPosition = viewHolder.getAdapterPosition();
                        List<ShoppingListEntry> shoppingListEntries =
                                mShoppingAdapter.getShoppingListEntries();
                        mDb.recipeDao().deleteIngredient(shoppingListEntries.get(adapterPosition));
                    }
                });
            }
        }).attachToRecyclerView(mShoppingBinding.rvShopping);
    }

    /**
     * Display the up button in the actionbar.
     */
    private void showUpButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                // Navigate back to previous screen when the up button pressed
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method will make the view for the shopping list visible.
     */
    private void showShoppingView() {
        // First, hide an empty view
        mShoppingBinding.tvEmptyShopping.setVisibility(View.GONE);
        // Then, make sure the shopping list data is visible
        mShoppingBinding.rvShopping.setVisibility(View.VISIBLE);
    }

    /**
     * When the shopping list is empty, show an empty view.
     */
    private void showEmptyView() {
        // First, hide the view for the shopping list
        mShoppingBinding.rvShopping.setVisibility(View.GONE);
        // Then, show an empty view
        mShoppingBinding.tvEmptyShopping.setVisibility(View.VISIBLE);
    }
}
