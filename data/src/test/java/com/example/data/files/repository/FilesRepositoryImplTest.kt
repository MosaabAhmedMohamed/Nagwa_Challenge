package com.example.data.files.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.core.test.util.TestingException
import com.example.data.factory.FilesFactory.generateDummyFilesLocalModels
import com.example.data.file.repository.FileRepositoryImpl
import com.example.data.file.source.local.FileLocalDataSource
import com.example.data.file.source.local.dao.FileDao
import com.example.data.file.source.local.model.FileLocalModel
import com.example.data.file.source.mapping.mapToDomain
import com.example.data.file.source.remote.FileDownloadDataSource
import com.google.gson.Gson
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
class FilesRepositoryImplTest {

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Mock
    lateinit var dao: FileDao

    @Mock
    lateinit var gson: Gson

    private lateinit var fileRepositoryImpl: FileRepositoryImpl

    private lateinit var fileDownloadDataSource: FileDownloadDataSource

    private lateinit var fileLocalDataSource: FileLocalDataSource

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        MockitoAnnotations.openMocks(this)
        fileLocalDataSource = FileLocalDataSource(context,dao,gson)
        fileDownloadDataSource = FileDownloadDataSource(context)
        fileRepositoryImpl =
            FileRepositoryImpl(
                fileLocalDataSource,
                fileDownloadDataSource)
    }

    @Test
    fun getItems_call_DAO() {
        // Arrange
        stubFileLocalDS(Flowable.just(listOf()))
        // Act
        fileRepositoryImpl.getFiles(false).test()

        // Assert
        Mockito.verify(dao).getFiles()
    }


    @Test
    fun getItems_returnEmptyList() {
        // Arrange
        stubFileLocalDS(Flowable.just(listOf()))
        // Act  // Assert
        fileRepositoryImpl
            .getFiles(false)
            .test()
            .assertValue {
                it.isEmpty()
            }


    }

    @Test
    fun getItems_returnError() {
        // Arrange
        val ex = TestingException()
        stubFileLocalDS(Flowable.error(ex))
        // Act  // Assert
        fileRepositoryImpl
            .getFiles(false)
            .test()
            .assertError(ex)


    }

    @Test
    fun getItems_returnListOfItemList() {
        // Arrange
        val items = generateDummyFilesLocalModels(5)
        stubFileLocalDS(Flowable.just(items))

        // Act  // Assert
        fileRepositoryImpl
            .getFiles(false)
            .test()
            .assertValue(items.mapToDomain())

    }

    private fun stubFileLocalDS(flowable: Flowable<List<FileLocalModel>>) {
        Mockito.`when`(dao.getFiles())
            .thenReturn(flowable)
    }

}