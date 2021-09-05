package com.koidev.tradernet.di

import dagger.Component

@Component(modules = [AppBindsModule::class])
@AppScope
class AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}