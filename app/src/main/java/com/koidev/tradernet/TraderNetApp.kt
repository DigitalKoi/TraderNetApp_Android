package com.koidev.tradernet

import android.app.Application
import timber.log.Timber

class TraderNetApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    private fun initDagger() {

    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}