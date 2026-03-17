package com.salmandev.imusic

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IMusicApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
