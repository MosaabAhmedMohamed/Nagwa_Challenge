package com.example.data.file.repository

import android.app.DownloadManager
import com.example.data.file.source.local.FileLocalDataSource
import com.example.data.file.source.mapping.mapToLocal
import com.example.data.file.source.remote.FileDownloadDataSource
import com.example.domain.file.model.FileDomainModel
import com.example.domain.file.repository.FileRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileLocalDataSource: FileLocalDataSource,
    private val fileDownloadDataSource: FileDownloadDataSource
) : FileRepository {


    override fun getFiles(): Flowable<List<FileDomainModel>> {
        return fileLocalDataSource.getFiles()
    }

    override fun downloadFile(
        folderPath: String,
        downloadUrl: String,
        name: String?
    ): Pair<DownloadManager?, Long?> {
        return fileDownloadDataSource.download(folderPath,downloadUrl,name)
    }

    override fun updateFileInLocal(file: FileDomainModel) {
        fileLocalDataSource.updateFileInLocal(file.mapToLocal())
    }

    override fun getFile(id: Int): Single<FileDomainModel> {
        return fileLocalDataSource.getFile(id)
    }

}