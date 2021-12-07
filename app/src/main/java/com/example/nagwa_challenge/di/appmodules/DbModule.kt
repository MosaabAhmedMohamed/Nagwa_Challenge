package com.example.nagwa_challenge.di.appmodules

import android.content.Context
import androidx.room.Room
import com.example.data.core.db.NagwaChallengeDatabase
import com.example.el_menus_challenge.util.data.DbConst
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideNagwaChallengeDatabase( context: Context): NagwaChallengeDatabase {
        return Room.databaseBuilder(context, NagwaChallengeDatabase::class.java, DbConst.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

}