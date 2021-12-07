package com.example.domain.file.usecase

import android.util.Log
import com.example.core.model.DownloadStatus
import com.example.domain.file.model.FileDomainModel
import com.example.domain.file.repository.FileRepository
import javax.inject.Inject

class UpdateFileUseCase @Inject constructor(private val fileRepository: FileRepository) {


    fun updateFileDownloadStatus(
        file: FileDomainModel?,
        status: DownloadStatus,
        folderPath: String
    ) {
        Log.d("testTAG", "updateFileDownloadStatus: ${status} ")
        file?.let {
            it.setDownloadStatus(status)
            it.setFilePath(folderPath)
            it.setDownloadTriesCount()
            fileRepository.updateFileInLocal(file)
        }
    }

    private fun FileDomainModel.setFilePath(folderPath: String) {
        localPath = folderPath
    }

    private fun FileDomainModel.setDownloadStatus(status: DownloadStatus) {
        downloadStatus = status
    }

    private fun FileDomainModel.setDownloadTriesCount() {
        if (downloadStatus == DownloadStatus.FAILED)
            downloadTriesCount += 1
    }
}