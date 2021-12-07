package com.example.data.file.source.mapping

import com.example.core.model.DownloadStatus
import com.example.data.file.source.local.model.FileLocalModel
import com.example.domain.file.model.FileDomainModel

fun List<FileLocalModel>.mapToDomain(): List<FileDomainModel> {
    return this.map { it.mapToDomain() }
}

fun FileLocalModel.mapToDomain(): FileDomainModel {
    return FileDomainModel(
        this.id,
        this.type,
        this.url,
        this.name,
        this.localPath,
        this.isDownloaded,
        this.downloadStatus?:DownloadStatus.NON
    )
}


fun FileDomainModel.mapToLocal(): FileLocalModel {

    return FileLocalModel(id, type, url, name, localPath, isDownloaded, downloadStatus)
}