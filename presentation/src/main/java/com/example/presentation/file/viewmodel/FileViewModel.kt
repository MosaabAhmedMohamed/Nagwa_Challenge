package com.example.presentation.file.viewmodel

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.model.DownloadStatus
import com.example.domain.file.usecase.DownloadFileUseCase
import com.example.domain.file.usecase.GetFilesUseCase
import com.example.domain.file.usecase.UpdateFileUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.base.DownloadStateRetriever
import com.example.presentation.base.SchedulerProvider
import com.example.presentation.file.mapping.mapToDomain
import com.example.presentation.file.mapping.mapToUIModel
import com.example.presentation.file.model.FileUiModel
import com.example.presentation.file.viewstate.FilesViewState
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class FileViewModel @Inject constructor(
    private val getFilesUseCase: GetFilesUseCase,
    private val updateFileUseCase: UpdateFileUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    var fileSelectedItem: FileUiModel? = null

    private val filesViewStateLDPrivate by lazy { MutableLiveData<FilesViewState>() }
    val filesViewStateLD: LiveData<FilesViewState> get() = filesViewStateLDPrivate

    fun getFiles(isForceRefresh: Boolean = false) {
        getFilesUseCase.getFiles(isForceRefresh)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .flatMap {
                Log.d("testTAG", "checkFilesToBeDownloaded: get")
                checkFilesToBeDownloaded(it.mapToUIModel())
                Flowable.just(it)
            }
            .doOnSubscribe {
                filesViewStateLDPrivate.postValue(FilesViewState.Loading)
            }
            .subscribe({
                filesViewStateLDPrivate.value = FilesViewState.onSuccess(it.mapToUIModel())
            }, {
                filesViewStateLDPrivate.value = FilesViewState.onError(it)
            })
            .addTo(compositeDisposable)
    }

    private fun checkFilesToBeDownloaded(files: List<FileUiModel>? = emptyList()) {
        files?.filter {
            it.downloadStatus == DownloadStatus.FAILED
        }?.filter {
            !it.localPath.isNullOrEmpty() && !it.url.isNullOrEmpty()
        }?.forEach {
            if (it.downloadTriesCount < 3) {
                fileSelectedItem = it
                downloadFile(it.localPath!!, it.url!!, it.name, it.id)
            }
        }
    }

    fun downloadFile(
        folderPath: String,
        downloadUrl: String,
        name: String?,
        itemId: Int?
    ) {
        updateDownloadStatus(DownloadStatus.PENDING, folderPath)
        val downloadInfo = downloadFileUseCase.downloadFile(folderPath, downloadUrl, name)
        if (downloadInfo.first != null && downloadInfo.second != null) {
            updateDownloadStatus(DownloadStatus.DOWNLOADING, folderPath)
            checkDownloadProgress(downloadInfo.first!!, downloadInfo.second!!, itemId, folderPath)
        } else {
            updateDownloadStatus(DownloadStatus.FAILED, folderPath)
        }
    }

    private fun updateDownloadStatus(status: DownloadStatus, folderPath: String) {
        updateFileUseCase.updateFileDownloadStatus(
            fileSelectedItem?.mapToDomain(),
            status,
            folderPath
        )
    }


    private fun checkDownloadProgress(
        dm: DownloadManager,
        downloadId: Long,
        itemId: Int?,
        folderPath: String
    ) {
        itemId?.let { itemId_ ->
            DownloadStateRetriever(dm).retrieve(downloadId, itemId_)
                .filter { it.first == itemId_ }
                .distinctUntilChanged()
                .flatMap {
                    checkIfDownloadCompleted(it.first, it.second, folderPath)
                    return@flatMap Observable.just(it)
                }.observeOn(schedulerProvider.ui())
                .subscribe()
                .addTo(compositeDisposable)
        }
    }

    private fun checkIfDownloadCompleted(itemId: Int, progress: Int, folderPath: String) {
        if (progress == 100) {
            getFilesUseCase.getFile(itemId)
                .subscribeOn(schedulerProvider.io())
                .subscribe { file ->
                    updateFileUseCase.updateFileDownloadStatus(
                        file,
                        DownloadStatus.DOWNLOADED,
                        "$folderPath/${file.name}"
                    )
                }
                .addTo(compositeDisposable)
        }
    }

    init {
        getFiles(false)
    }

}

