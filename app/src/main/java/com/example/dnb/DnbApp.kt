package com.example.dnb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DnbApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}