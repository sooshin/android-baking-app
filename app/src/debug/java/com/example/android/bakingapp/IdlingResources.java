package com.example.android.bakingapp;

import android.support.test.espresso.IdlingRegistry;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import okhttp3.OkHttpClient;

/**
 * Reference: @see "https://caster.io/lessons/espreso-idling-registry-for-okhttp/"
 */
public abstract class IdlingResources {

    public static void registerOkHttp(OkHttpClient client) {
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create(
                "okHttp", client));
    }
}
