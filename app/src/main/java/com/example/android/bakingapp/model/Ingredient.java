package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    @SerializedName("quantity")
    private double mQuantity;

    @SerializedName("measure")
    private String mMeasure;

    @SerializedName("ingredient")
    private String mIngredient;

    private Ingredient(Parcel in) {
        mQuantity = in.readDouble();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public void setQuantity(double quantity) {
        mQuantity = quantity;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public void setMeasure(String measure) {
        mMeasure = measure;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setIngredient(String ingredient) {
        mIngredient = ingredient;
    }

    public String getIngredient() {
        return mIngredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
}
