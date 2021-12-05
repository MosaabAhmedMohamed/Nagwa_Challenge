package com.example.nagwa_challenge.di


import com.example.nagwa_challenge.NavHostActivity
import com.example.nagwa_challenge.di.tags.TagsFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {


    @ContributesAndroidInjector(
        modules = [
            TagsFragmentBuilderModule::class
        ]
    )
    abstract fun provideNavHostActivity(): NavHostActivity
}