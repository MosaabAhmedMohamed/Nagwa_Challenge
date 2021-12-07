package com.example.presentation.file.viewstate

import com.example.presentation.file.model.FileUiModel


sealed class FilesViewState {

    object Loading : FilesViewState()
    data class onSuccess(val result: List<FileUiModel>) : FilesViewState()
    data class onError(val error: Throwable) : FilesViewState()
}