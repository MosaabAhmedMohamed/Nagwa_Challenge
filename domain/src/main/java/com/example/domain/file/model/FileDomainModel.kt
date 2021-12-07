package com.example.domain.file.model

import com.example.core.model.DownloadStatus


data class FileDomainModel(
    val id: Int? = 0,
    val type: String?,
    val url: String?,
    val name: String?,
    var localPath: String?,
    var downloadStatus: DownloadStatus = DownloadStatus.NON,
)
