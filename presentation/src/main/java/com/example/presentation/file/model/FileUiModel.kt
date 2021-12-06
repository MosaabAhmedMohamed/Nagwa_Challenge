package com.example.presentation.file.model

data class FileUiModel(
    val id: Int? = 0,
    val type: FileType?,
    val url: String?,
    val name: String?,
    var localPath: String?,
    var isDownloaded: Boolean = false
)
