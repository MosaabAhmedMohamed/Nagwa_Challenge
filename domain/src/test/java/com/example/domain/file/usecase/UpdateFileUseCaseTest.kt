package com.example.domain.file.usecase

import com.example.core.model.DownloadStatus
import com.example.domain.file.factory.FilesFactory.generateFileModel
import com.example.domain.file.repository.FileRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class UpdateFileUseCaseTest {

    @Mock
    lateinit var fileRepository: FileRepository

    private
    lateinit var useCase: UpdateFileUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = UpdateFileUseCase(fileRepository)
    }

    @Test
    fun updateFileDownloadedStatus_call_repository() {
        // Arrange
        // No Arrangement for this test case
        val model = generateFileModel()

        // Act
        useCase.updateFileDownloadedStatus(model, "")

        // Assert
        Mockito.verify(fileRepository).updateFileInLocal(model)
    }


    @Test
    fun updateFileDownloadStatus_call_repository() {
        // Arrange
        // No Arrangement for this test case
        val model = generateFileModel()

        // Act
        useCase.updateFileDownloadStatus(model, DownloadStatus.DOWNLOADING)

        // Assert
        Mockito.verify(fileRepository).updateFileInLocal(model)
    }


}