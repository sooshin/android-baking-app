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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.data.RecipeRepository;
import com.example.android.bakingapp.data.ShoppingListEntry;

import java.util.List;

/**
 * {@link ViewModel} for ShoppingActivity
 */
public class ShoppingViewModel extends ViewModel {

    private final RecipeRepository mRepository;
    private final LiveData<List<ShoppingListEntry>> mList;

    public ShoppingViewModel(RecipeRepository repository) {
        mRepository = repository;
        mList = mRepository.getAllShoppingList();
    }

    /**
     * Returns {@link LiveData} with the list of ShoppingListEntries
     */
    public LiveData<List<ShoppingListEntry>> getList() {
        return mList;
    }
}
