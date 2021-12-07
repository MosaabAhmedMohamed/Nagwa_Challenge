package com.example.domain.file.usecase

import com.example.domain.file.factory.FilesFactory
import com.example.domain.file.repository.FileRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class DownloadFileUseCaseTest {

    @Mock
    lateinit var fileRepository: FileRepository

    private
    lateinit var useCase: DownloadFileUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = DownloadFileUseCase(fileRepository)
    }

    @Test
    fun updateFileDownloadedStatus_call_repository() {
        // Arrange
        // No Arrangement for this test case
        val model = FilesFactory.generateFileModel()

        // Act
        useCase.downloadFile("","", "")

        // Assert
        Mockito.verify(fileRepository).downloadFile("","", "")
    }


}