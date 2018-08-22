package com.example.android.bakingapp.utilities;

import android.support.constraint.BuildConfig;

import com.example.android.bakingapp.IdlingResources;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static com.example.android.bakingapp.utilities.Constant.BAKING_BASE_URL;

/**
 *  Create a singleton of Retrofit.
 *
 *  Reference: @see "https://medium.com/@wingoku/synchronization-between-retrofit-espresso-achieved-9540fe95d357"
 *  "https://caster.io/lessons/espreso-idling-registry-for-okhttp/"
 */
public class RetrofitClient {

    /** Static variable for Retrofit */
    private static Retrofit sRetrofit = null;

    public static Retrofit getClient() {
        if (sRetrofit == null) {
            OkHttpClient client = new OkHttpClient();

            // Register the idling resource
            if (BuildConfig.DEBUG) {
                IdlingResources.registerOkHttp(client);
            }

            Timber.e("Instance class created");
            OkHttpClient.Builder okHttpClientBuilder = client.newBuilder();
            okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(20, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                Timber.e("Debug build");
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(interceptor);
            }

            // Create the Retrofit instance using the builder
            sRetrofit = new Retrofit.Builder()
                    .client(okHttpClientBuilder.build())
                    // Set the base URL
                    .baseUrl(BAKING_BASE_URL)
                    // Use GsonConverterFactory class to generate an implementation of the baking interface
                    // which uses Gson for its deserialization
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
