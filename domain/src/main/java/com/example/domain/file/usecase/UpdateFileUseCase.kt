package com.example.domain.file.usecase

import com.example.domain.file.model.FileDomainModel
import com.example.domain.file.repository.FileRepository
import javax.inject.Inject

class UpdateFileUseCase @Inject constructor(private val fileRepository: FileRepository) {

    fun updateFileDownloadStatus(
        file: FileDomainModel?,
        folderPath: String
    ) {
        file?.let {
            setFilePathAndStatus(file, folderPath)
            fileRepository.updateFileInLocal(file)
        }
    }

    private fun setFilePathAndStatus(file: FileDomainModel, folderPath: String) {
        file.localPath = folderPath
        file.isDownloaded = true
    }
}