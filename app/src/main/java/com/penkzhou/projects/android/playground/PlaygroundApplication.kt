package com.penkzhou.projects.android.playground

import android.app.Application
import android.content.Context

import com.facebook.stetho.Stetho

class PlaygroundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PlaygroundApplication.appContext = applicationContext
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}
