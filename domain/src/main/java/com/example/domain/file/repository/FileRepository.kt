package com.example.domain.file.repository

import android.app.DownloadManager
import com.example.domain.file.model.FileDomainModel
import io.reactivex.Flowable
import io.reactivex.Single

interface FileRepository {

    fun getFiles(isForceRefresh: Boolean): Flowable<List<FileDomainModel>>

    fun downloadFile(
        folderPath: String,
        downloadUrl: String,
        name: String?
    ): Pair<DownloadManager?, Long?>

    fun updateFileInLocal(file: FileDomainModel)

    fun getFile(id: Int): Single<FileDomainModel>
}