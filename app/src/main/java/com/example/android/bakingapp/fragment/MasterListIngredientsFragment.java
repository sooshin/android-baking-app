package com.example.android.bakingapp.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.adapter.IngredientsAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.FragmentMasterListIngredientsBinding;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.utilities.Constant.EXTRA_RECIPE;

public class MasterListIngredientsFragment extends Fragment{

    private Recipe mRecipe;

    private IngredientsAdapter mIngredientsAdapter;

    private FragmentMasterListIngredientsBinding mMasterListBinding;

    // Mandatory empty constructor
    public MasterListIngredientsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Instantiate FragmentMasterListIngredientsBinding using DataBindingUtil
        mMasterListBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_master_list_ingredients, container, false);

        View rootView = mMasterListBinding.getRoot();

        mRecipe = getRecipeData();
        List<Ingredient> ingredients = new ArrayList<>();

        mIngredientsAdapter = new IngredientsAdapter(ingredients);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mMasterListBinding.rvIngredients.setLayoutManager(layoutManager);
        mMasterListBinding.rvIngredients.setHasFixedSize(true);

        mMasterListBinding.rvIngredients.setAdapter(mIngredientsAdapter);

        mIngredientsAdapter.addAll(mRecipe.getIngredients());

        return rootView;
    }

    private Recipe getRecipeData() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_RECIPE)) {
                Bundle b = intent.getBundleExtra(EXTRA_RECIPE);
                mRecipe = b.getParcelable(EXTRA_RECIPE);
            }
        }
        return mRecipe;
    }
}
