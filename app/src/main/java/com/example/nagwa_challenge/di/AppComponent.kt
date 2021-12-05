package com.example.nagwa_challenge.di

import android.app.Application
import com.example.nagwa_challenge.app.NaqwaChallenge
import com.example.nagwa_challenge.di.appmodules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class]
)
interface AppComponent {
    fun inject(app: NaqwaChallenge)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}