package com.example.domain.file.usecase

import android.app.DownloadManager
import com.example.domain.file.repository.FileRepository
import javax.inject.Inject

class DownloadFileUseCase @Inject constructor(private val fileRepository: FileRepository) {

    fun downloadFile(
        folderPath: String,
        downloadUrl: String,
        name: String?
    ): Pair<DownloadManager?, Long?> {
        return fileRepository.downloadFile(folderPath, downloadUrl, name)
    }
}