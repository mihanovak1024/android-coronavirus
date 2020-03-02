package com.mihanovak1024.coronavirus

import android.app.Application
import timber.log.Timber

class CoronaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}