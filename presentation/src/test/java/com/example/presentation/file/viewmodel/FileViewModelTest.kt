package com.example.presentation.file.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.core.test.util.TestingException
import com.example.domain.file.model.FileDomainModel
import com.example.domain.file.repository.FileRepository
import com.example.domain.file.usecase.DownloadFileUseCase
import com.example.domain.file.usecase.GetFilesUseCase
import com.example.domain.file.usecase.UpdateFileUseCase
import com.example.presentation.base.AppSchedulerProvider
import com.example.presentation.base.SchedulerProvider
import com.example.presentation.factory.FileFactory.generateDomainDummyFileModels
import com.example.presentation.file.mapping.mapToUIModel
import com.example.presentation.file.viewstate.FilesViewState
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FileViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var fileRepository: FileRepository

    @Mock
    private lateinit var stateObserver: Observer<FilesViewState>

    private lateinit var schedulerProvider: SchedulerProvider

    private lateinit var getFilesUseCase: GetFilesUseCase

    private lateinit var updateFileUseCase: UpdateFileUseCase

    private lateinit var downloadFileUseCase: DownloadFileUseCase

    private lateinit var viewmodel: FileViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        setUpSchedulers()
        setUpUseCases()
        setUpViewModel()
    }

    private fun setUpSchedulers() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        schedulerProvider = AppSchedulerProvider()
    }

    private fun setUpUseCases() {
        getFilesUseCase =
            GetFilesUseCase(fileRepository)

        updateFileUseCase =
            UpdateFileUseCase(fileRepository)

        downloadFileUseCase =
            DownloadFileUseCase(fileRepository)
    }

    private fun setUpViewModel() {
        viewmodel = FileViewModel(
            getFilesUseCase,
            updateFileUseCase,
            downloadFileUseCase,
            schedulerProvider
        )
        viewmodel.filesViewStateLD.observeForever(stateObserver)
    }

    @Test
    fun geFiles_returnsEmpty() {
        // Arrange
        stubFetchItems(Flowable.just(listOf()))

        // Act
        viewmodel.getFiles(false)

        // Assert
        Mockito.verify(stateObserver).onChanged(FilesViewState.Loading)
    }

    @Test
    fun getFiles_returnsError() {
        // Arrange
        val ex = TestingException(TestingException.GENERIC_EXCEPTION_MESSAGE)
        stubFetchItems(Flowable.error(ex))

        // Act
        viewmodel.getFiles(false)

        // Assert
        Mockito.verify(stateObserver).onChanged(FilesViewState.Loading)
        Mockito.verify(stateObserver).onChanged(FilesViewState.onError(ex))
    }

    @Test
    fun getFiles_returnsData() {
        // Arrange
        val items = generateDomainDummyFileModels(10)
        stubFetchItems(Flowable.just(items))

        // Act
        viewmodel.getFiles(false)

        // Assert
        Mockito.verify(stateObserver).onChanged(FilesViewState.Loading)
        Mockito.verify(stateObserver).onChanged(FilesViewState.onSuccess(items.mapToUIModel()))
    }



    /**
     * Stub Helpers Methods
     */

    private fun stubFetchItems(flowable: Flowable<List<FileDomainModel>>) {
        Mockito.`when`(getFilesUseCase.getFiles(false))
            .thenReturn(flowable)
    }


}