package com.koidev.tradernet

import android.app.Application
import android.content.Context
import com.koidev.tradernet.di.AppComponent
import com.koidev.tradernet.di.DaggerAppComponent
import timber.log.Timber

class TraderNetApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TraderNetApp -> appComponent
        else -> applicationContext.appComponent
    }
