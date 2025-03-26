package com.example.languageapp.feature_app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log

class NetworkConnection(
    private val context: Context
) {

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addCapability(NetworkCapabilities.NET_CAPABILITY_MMS)
        .build()

    val callback = object : ConnectivityManager.NetworkCallback(){
        override fun onLost(network: Network) {
            super.onLost(network)
            Log.e("callback", "lost")
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.e("callback", "available")
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            Log.e("callback", "change")
        }
    }

    fun t(){

    }
}