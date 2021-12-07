package com.example.domain.file.usecase

import com.example.core.test.util.TestingException
import com.example.domain.file.factory.FilesFactory
import com.example.domain.file.model.FileDomainModel
import com.example.domain.file.repository.FileRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetFilesUseCaseTest {

    @Mock
    lateinit var fileRepository: FileRepository

    private
    lateinit var useCase: GetFilesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetFilesUseCase(fileRepository)
    }

    @Test
    fun getItems_call_repository() {
        // Arrange
        // No Arrangement for this test case

        // Act
        useCase.getFiles( false)

        // Assert
        Mockito.verify(fileRepository).getFiles(false)
    }

    @Test
    fun getItems_completes() {
        // Arrange
        stubItems(Flowable.just(FilesFactory.generateDummyFileModels(3)))

        // Act // Assert
        useCase.getFiles( false)
            .test()
            .assertComplete()

    }

    @Test
    fun getItems_returnsData() {
        // Arrange
        val products = FilesFactory.generateDummyFileModels(4)
        stubItems(Flowable.just(products))

        // Act   // Assert
        useCase.getFiles( false)
            .test()
            .assertValue(products)

    }

    @Test
    fun getItems_returnsError() {
        // Arrange
        val ex = TestingException()
        stubItems(Flowable.error(ex))

        // Act   // Assert
        useCase.getFiles( false)
            .test()
            .assertError {
                it == ex
            }

    }


    private fun stubItems(flowable: Flowable<List<FileDomainModel>>) {
        Mockito.`when`(fileRepository.getFiles(false))
            .thenReturn(flowable)
    }
}