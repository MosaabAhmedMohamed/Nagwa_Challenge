package com.example.data.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.file.source.local.dao.FileDao
import com.example.data.file.source.local.model.FileLocalModel

@Database(
    entities = [FileLocalModel::class],
    version = NAGWA_Challenge_DATABASE_VERSION_NUMBER
)


abstract class NagwaChallengeDatabase : RoomDatabase() {

   abstract fun fileDao(): FileDao

}

const val NAGWA_Challenge_DATABASE_VERSION_NUMBER = 1

