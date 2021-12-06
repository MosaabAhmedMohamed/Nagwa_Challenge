package com.example.domain.file.model


data class FileDomainModel(
    val id: Int? = 0,
    val type: String?,
    val url: String?,
    val name: String?,
    var localPath: String?,
    var isDownloaded: Boolean = false,
)
