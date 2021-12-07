package com.example.data.files.source.local.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.core.db.NagwaChallengeDatabase
import com.example.data.factory.FilesFactory
import com.example.data.file.source.local.dao.FileDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class FileDaoTest {

    @get:Rule
    var instantTask = InstantTaskExecutorRule()


    private lateinit var database: NagwaChallengeDatabase

    private lateinit var dao: FileDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, NagwaChallengeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.fileDao()
    }

    @Test
    fun getCachedItems_returnsData() {
        val items = FilesFactory.generateDummyFilesLocalModels(5)
        dao.deleteAllFiles()
        dao.insertFiles(items).test()

        // Act   // Assert
        dao.getFiles()
            .test()
            .assertValue {
                it == items
            }
    }

    @Test
    fun deleteEntries_returnsEmpty() {
        dao.deleteAllFiles()
        // Act   // Assert
        dao.getFiles()
            .test()
            .assertValue {
                it.isNullOrEmpty()
            }

    }

    @After
    fun tearDown() {
        database.close()
    }

}