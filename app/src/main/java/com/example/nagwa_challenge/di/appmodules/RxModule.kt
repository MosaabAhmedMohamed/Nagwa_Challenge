package com.example.nagwa_challenge.di.appmodules


import com.example.presentation.base.AppSchedulerProvider
import com.example.presentation.base.SchedulerProvider
import dagger.Module
import dagger.Provides


@Module
class RxModule {


    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }


}