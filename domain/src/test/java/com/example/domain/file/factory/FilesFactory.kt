package com.example.domain.file.factory

import com.example.core.test.util.DataFactory
import com.example.domain.file.model.FileDomainModel

object FilesFactory {


    fun generateDummyFileModels(size: Int): List<FileDomainModel> {
        val productList = mutableListOf<FileDomainModel>()
        repeat(size) {
            productList.add(generateFileModel())
        }
        return productList
    }

    fun generateFileModel(): FileDomainModel {
        return FileDomainModel(
            DataFactory.getRandomInt(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
            DataFactory.getRandomString(),
        )
    }


}