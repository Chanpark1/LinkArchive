package com.chanyoung.jack.application

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ArchiveApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}