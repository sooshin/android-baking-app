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

package com.example.android.bakingapp.utilities;

import com.example.android.bakingapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

import static com.example.android.bakingapp.utilities.Constant.CONNECT_TIMEOUT_TEN;
import static com.example.android.bakingapp.utilities.Constant.READ_TIMEOUT_TWENTY;

/**
 * Creates OkHttpClient.
 *
 * Reference: @see "https://medium.com/@wingoku/synchronization-between-retrofit-espresso-achieved-9540fe95d357"
 * "https://github.com/chiuki/espresso-samples/tree/master/idling-resource-okhttp"
 */
public abstract class OkHttpProvider {

    private static OkHttpClient sInstance = null;

    public static OkHttpClient getOkHttpInstance() {
        if (sInstance == null) {
            sInstance = new OkHttpClient();

            Timber.e("Instance class created");
            OkHttpClient.Builder okHttpClientBuilder = sInstance.newBuilder();
            okHttpClientBuilder.connectTimeout(CONNECT_TIMEOUT_TEN, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(READ_TIMEOUT_TWENTY, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                Timber.e("Debug build");
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(interceptor);
            }
        }
        return sInstance;
    }
}
