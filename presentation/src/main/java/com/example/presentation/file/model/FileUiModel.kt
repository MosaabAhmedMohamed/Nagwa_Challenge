package com.example.presentation.file.model

import com.example.core.model.DownloadStatus

data class FileUiModel(
    val id: Int? = 0,
    val type: FileType?,
    val url: String?,
    val name: String?,
    var localPath: String?,
    var isDownloaded: Boolean = false,
    var downloadStatus: DownloadStatus = DownloadStatus.NON
)
