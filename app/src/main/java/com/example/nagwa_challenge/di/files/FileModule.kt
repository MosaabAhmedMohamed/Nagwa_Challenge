package com.example.nagwa_challenge.di.files

import android.content.Context
import com.example.data.core.db.NagwaChallengeDatabase
import com.example.data.file.repository.FileRepositoryImpl
import com.example.data.file.source.local.FileLocalDataSource
import com.example.data.file.source.remote.FileDownloadDataSource
import com.example.domain.file.repository.FileRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class FileModule {


    @Provides
    fun provideFileLocalDataSource(
        context: Context,
        db: NagwaChallengeDatabase,
        gson: Gson
    ): FileLocalDataSource =
        FileLocalDataSource(
            context,
            db.fileDao(),
            gson
        )

    @Provides
    fun provideFileDownloadDataSource(
        context: Context
    ): FileDownloadDataSource =
        FileDownloadDataSource(context)

    @Provides
    fun provideTutorialsRepository(
        fileLocalDataSource: FileLocalDataSource,
        fileDownloadDataSource: FileDownloadDataSource
    ): FileRepository =
        FileRepositoryImpl(fileLocalDataSource, fileDownloadDataSource)

}