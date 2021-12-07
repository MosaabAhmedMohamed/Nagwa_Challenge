package com.example.nagwa_challenge.di


import com.example.nagwa_challenge.NavHostActivity
import com.example.nagwa_challenge.di.files.FileFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector(
        modules = [
            FileFragmentBuilderModule::class
        ]
    )
    abstract fun provideNavHostActivity(): NavHostActivity
}