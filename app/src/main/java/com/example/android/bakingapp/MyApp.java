package com.example.android.bakingapp;

import android.app.Application;

/**
 * The MyApp class is called whenever the app is launched.
 */
public class MyApp extends Application {

    private static MyApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static synchronized MyApp getInstance() {
        return sInstance;
    }

    /**
     * This method is used to initiate the connectivity listener
     * @param listener ConnectivityReceiverListener which is triggered whenever the network status
     *                 changes.
     */
    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.sReceiverListener = listener;
    }
}
