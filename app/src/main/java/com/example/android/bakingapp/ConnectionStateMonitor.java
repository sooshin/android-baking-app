package com.example.android.bakingapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

/**
 * refrenece: @see "https://stackoverflow.com/questions/36421930/connectivitymanager-connectivity-action-deprecated/36447866#36447866"
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ConnectionStateMonitor extends ConnectivityManager.NetworkCallback {

    private NetworkRequest mNetworkRequest;

    public ConnectionStateMonitor() {
        mNetworkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
    }

    public void enable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerNetworkCallback(mNetworkRequest, this);
    }

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);

        if (ConnectivityReceiver.sReceiverListener != null) {
            ConnectivityReceiver.sReceiverListener.onNetworkConnectionChanged(true);
        }
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);

        if (ConnectivityReceiver.sReceiverListener != null) {
            ConnectivityReceiver.sReceiverListener.onNetworkConnectionChanged(false);
        }
    }
}
