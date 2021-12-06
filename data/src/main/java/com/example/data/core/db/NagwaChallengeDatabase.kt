package com.example.data.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = NAGWA_Challenge_DATABASE_VERSION_NUMBER
)


abstract class NagwaChallengeDatabase : RoomDatabase() {

   // abstract fun tagsDao(): TagsDao

}

const val NAGWA_Challenge_DATABASE_VERSION_NUMBER = 1

