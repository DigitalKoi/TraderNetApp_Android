package com.koidev.tradernet.di

import com.koidev.tradernet.BuildConfig
import com.koidev.tradernet.TraderNetApp
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
    fun provideMoshi() = Moshi.Builder().build()

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

    fun provideScarlet(
        application: TraderNetApp,
        client: OkHttpClient,
        moshi: Moshi,
        @BaseUrl
        uri: String
    ): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory(uri))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
            .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
            .build()
    }
}