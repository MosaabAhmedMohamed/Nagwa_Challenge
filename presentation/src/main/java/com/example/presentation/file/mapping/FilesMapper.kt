package com.example.presentation.file.mapping

import com.example.domain.file.model.FileDomainModel
import com.example.presentation.file.model.FileType
import com.example.presentation.file.model.FileUiModel

fun List<FileDomainModel>.mapToUIModel(): MutableList<FileUiModel> {
    val uiModels = mutableListOf<FileUiModel>()
    forEach {
        uiModels.add(it.mapToUIModel(it.mapFileType()))
    }
    return uiModels
}

fun FileDomainModel.mapFileType(): FileType {
    return when (type?.uppercase()) {
        "PDF" -> FileType.PDF
        else -> FileType.VIDEO
    }
}

fun FileDomainModel.mapToUIModel(fileType: FileType): FileUiModel {
    return FileUiModel(id, fileType, url, name, localPath, isDownloaded)
}