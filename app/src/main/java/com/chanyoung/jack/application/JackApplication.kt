package com.chanyoung.jack.application

import android.app.Application
import com.chanyoung.jack.data.room.database.JDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}