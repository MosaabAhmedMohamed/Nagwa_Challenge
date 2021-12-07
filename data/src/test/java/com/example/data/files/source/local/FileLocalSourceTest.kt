package com.example.data.files.source.local


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.data.factory.FilesFactory.generateDummyFilesLocalModels
import com.example.data.file.source.local.FileLocalDataSource
import com.example.data.file.source.local.dao.FileDao
import com.example.data.file.source.local.model.FileLocalModel
import com.example.data.file.source.mapping.mapToDomain
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class FileLocalSourceTest {

    @get:Rule
    var instantTask = InstantTaskExecutorRule()


    @Mock
    lateinit var dao: FileDao

    @Mock
    lateinit var gson: Gson

    private lateinit var itemListLocalDataSource: FileLocalDataSource

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        MockitoAnnotations.openMocks(this)
        itemListLocalDataSource = FileLocalDataSource(context, dao, gson)
    }


    @Test
    fun cacheItems_call_dao() {
        // Arrange
        // No Arrangement for this test case
        val items = generateDummyFilesLocalModels(5)
        stubCacheItems(items)
        // Act
        itemListLocalDataSource
            .cacheFiles(items)

        // Assert
        Mockito.verify(dao).deleteAllFiles()
        Mockito.verify(dao).insertFiles(items)
    }

    @Test
    fun getCachedItems_returnsData() {
        // Arrange
        val items = generateDummyFilesLocalModels(5)
        stubGetItems(Flowable.just(items))

        // Act   // Assert
        itemListLocalDataSource
            .getFiles(false)
            .test()
            .assertValue(items.mapToDomain())

    }

    private fun stubGetItems(flowable: Flowable<List<FileLocalModel>>) {
        Mockito.`when`(dao.getFiles())
            .thenReturn(flowable)
    }

    private fun stubCacheItems(items: List<FileLocalModel>) {
        Mockito.`when`(dao.deleteAllFiles())
            .thenReturn(Completable.complete())

        Mockito.`when`(dao.insertFiles(items))
            .thenReturn(Completable.complete())
    }


}