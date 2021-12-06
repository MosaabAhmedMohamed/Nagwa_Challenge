package com.example.presentation.file.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.file.usecase.GetFilesUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.base.SchedulerProvider
import com.example.presentation.file.mapping.mapToUIModel
import com.example.presentation.file.model.FileUiModel
import com.example.presentation.file.viewstate.FilesViewState
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class FileViewModel @Inject constructor(
    private val getFilesUseCase: GetFilesUseCase,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {


    var fileSelectedItem: FileUiModel? = null


    private val filesViewStateLDPrivate by lazy { MutableLiveData<FilesViewState>() }
    val filesViewStateLD: LiveData<FilesViewState> get() = filesViewStateLDPrivate

    private val downloadProgressLDPrivate by lazy { MutableLiveData<Int>() }
    val downloadProgressLD get() = downloadProgressLDPrivate

    fun getFiles() {
        getFilesUseCase.getFiles()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
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

}

