package com.example.data.factory

import com.example.core.test.util.DataFactory
import com.example.data.file.source.local.model.FileLocalModel
import com.example.domain.file.model.FileDomainModel


object FilesFactory {


    fun generateDummyFilesLocalModels(size: Int): List<FileLocalModel> {
        val productList = mutableListOf<FileLocalModel>()
        repeat(size) {
            productList.add(generateFilesLocalModel())
        }
        return productList
    }

    fun generateFilesLocalModel(): FileLocalModel {
        return FileLocalModel(
            DataFactory.getRandomInt(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
        )
    }

    fun generateFilesDomainModel(): FileDomainModel {
        return FileDomainModel(
            DataFactory.getRandomInt(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
        )
    }


}