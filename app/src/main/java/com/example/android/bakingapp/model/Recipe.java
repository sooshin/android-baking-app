package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("ingredients")
    private List<Ingredient> mIngredients = null;

    @SerializedName("steps")
    private List<Step> mSteps = null;

    @SerializedName("servings")
    private int mServings;

    @SerializedName("image")
    private String mImage;

    public Recipe(int recipeId, String name, List<Ingredient> ingredients, List<Step> steps,
                  int servings, String image) {
        mId = recipeId;
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImage = image;
    }

    private Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        if (in.readByte() == 0x01) {
            mIngredients = new ArrayList<>();
            in.readList(mIngredients, Ingredient.class.getClassLoader());
        } else {
            mIngredients = null;
        }
        if (in.readByte() == 0x01) {
            mSteps = new ArrayList<>();
            in.readList(mSteps, Step.class.getClassLoader());
        } else {
            mSteps = null;
        }
        mServings = in.readInt();
        mImage = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setServings(int servings) {
        mServings = servings;
    }

    public int getServings() {
        return mServings;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getImage() {
        return mImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        if (mIngredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mIngredients);
        }
        if (mSteps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mSteps);
        }
        dest.writeInt(mServings);
        dest.writeString(mImage);
    }
}
