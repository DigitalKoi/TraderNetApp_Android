package com.koidev.tradernet.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.koidev.tradernet.MainViewModel
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(modules = [
    AppBindsModule::class,
    NetworkModule::class
])
@AppScope
interface AppComponent {

    fun application(): Application

    val factory: MultiViewModelFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}

@Module
interface AppBindsModule {

    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel
}
