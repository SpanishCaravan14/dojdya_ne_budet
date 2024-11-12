package com.example.dnb

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DnbApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}