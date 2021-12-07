package com.example.presentation.factory

import com.example.core.test.util.DataFactory
import com.example.domain.file.model.FileDomainModel
import com.example.presentation.file.model.FileType
import com.example.presentation.file.model.FileUiModel


object FileFactory {

    fun generateDomainDummyFileModels(size: Int): List<FileDomainModel> {
        val productList = mutableListOf<FileDomainModel>()
        repeat(size) {
            productList.add(generateDomainFileModel())
        }
        return productList
    }

    fun generateDomainFileModel(): FileDomainModel {
        return FileDomainModel(
            DataFactory.getRandomInt(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
        )
    }



    fun generateDummyFileModels(size: Int): List<FileUiModel> {
        val productList = mutableListOf<FileUiModel>()
        repeat(size) {
            productList.add(generateFileModel())
        }
        return productList
    }

    fun generateFileModel(): FileUiModel {
        return FileUiModel(
            DataFactory.getRandomInt(),
            FileType.PDF,
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
        )
    }


    fun generateFileDomainModel(): FileDomainModel {
        return FileDomainModel(
            DataFactory.getRandomInt(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
        )
    }


}