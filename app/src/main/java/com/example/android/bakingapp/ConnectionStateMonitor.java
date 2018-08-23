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

package com.example.android.bakingapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

/**
 * Use NetworkCallback because the ability for a backgrounded application to receive network
 * connection state changes, android.net.conn.CONNECTIVITY_CHANGE, is deprecated for apps
 * targeting Android N or higher.
 *
 * Reference: @see "https://stackoverflow.com/questions/36421930/connectivitymanager-connectivity-
 * action-deprecated/36447866#36447866"
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
