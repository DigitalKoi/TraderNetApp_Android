package com.koidev.tradernet.di

import android.app.Application
import android.content.Context
import android.telecom.ConnectionService
import com.koidev.tradernet.BuildConfig
import com.koidev.tradernet.TraderNetApp
import com.koidev.tradernet.service.CoinbaseService
import com.koidev.tradernet.service.FlowStreamAdapter
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class NetworkModule {

    @Provides
    @AppScope
    @BaseUrl
    fun provideApiBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @AppScope
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @AppScope
    fun provideHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
                else HttpLoggingInterceptor.Level.NONE
            )

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @AppScope
    fun provideScarlet(
        application: Application,
        @BaseUrl uri: String,
        client: OkHttpClient,
        moshi: Moshi
    ): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory(uri))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
            .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
            .build()
    }

    @Provides
    @AppScope
    fun provideCoinbaseService(scarlet: Scarlet): CoinbaseService = scarlet.create()
}