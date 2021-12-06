package com.example.nagwa_challenge.di.files


import com.example.presentation.file.ui.fragment.FileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module(includes = [FileModule::class])
abstract class FileFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun provideFileFragment(): FileFragment

}