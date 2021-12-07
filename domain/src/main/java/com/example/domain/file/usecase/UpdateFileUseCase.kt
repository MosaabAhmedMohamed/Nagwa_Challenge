package com.example.domain.file.usecase

import com.example.core.model.DownloadStatus
import com.example.domain.file.model.FileDomainModel
import com.example.domain.file.repository.FileRepository
import javax.inject.Inject

class UpdateFileUseCase @Inject constructor(private val fileRepository: FileRepository) {

    fun updateFileDownloadedStatus(
        file: FileDomainModel?,
        folderPath: String
    ) {
        file?.let {
            it.setFilePath(folderPath)
            it.setDownloadStatus(DownloadStatus.DOWNLOADED)
            fileRepository.updateFileInLocal(file)
        }
    }

    fun updateFileDownloadStatus(
        file: FileDomainModel?,
        status: DownloadStatus
    ){
        file?.let {
            it.setDownloadStatus(status)
            fileRepository.updateFileInLocal(file)
        }
    }

    private fun FileDomainModel.setFilePath(folderPath: String) {
        localPath = folderPath
    }

    private fun FileDomainModel.setDownloadStatus(status: DownloadStatus) {
        downloadStatus = status
    }
}