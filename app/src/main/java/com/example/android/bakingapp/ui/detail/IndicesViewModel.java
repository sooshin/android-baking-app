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

package com.example.android.bakingapp.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.data.RecipeRepository;

import java.util.List;

/**
 * {@link ViewModel} for {@link MasterListIngredientsFragment}
 */
public class IndicesViewModel extends ViewModel {

    private final RecipeRepository mRepository;

    /** The list of ingredient indices which exist in the shopping list database */
    private LiveData<List<Integer>> mIndices;

    public IndicesViewModel(RecipeRepository repository, String recipeName) {
        mRepository = repository;
        mIndices = mRepository.getIndices(recipeName);
    }

    /**
     * Returns {@link LiveData} with the list of ingredient indices which exist in
     * the shopping list database.
     */
    public LiveData<List<Integer>> getIndices() {
        return mIndices;
    }
}
