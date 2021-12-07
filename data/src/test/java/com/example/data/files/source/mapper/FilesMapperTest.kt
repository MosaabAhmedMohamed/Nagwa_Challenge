package com.example.data.files.source.mapper

import com.example.data.factory.FilesFactory
import com.example.data.file.source.local.model.FileLocalModel
import com.example.data.file.source.mapping.mapToDomain
import com.example.data.file.source.mapping.mapToLocal
import com.example.domain.file.model.FileDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FilesMapperTest {


    @Test
    fun mapToLocal() {
        // Arrange
        val itemDomain = FilesFactory.generateFilesDomainModel()

        // Act
        val itemLocalModel = itemDomain.mapToLocal()

        // Assert
        assertItemMapDataEqual(itemLocalModel, itemDomain)
    }

    /**
     * Helpers Methods
     */
    private fun assertItemMapDataEqual(itemLocalModel: FileLocalModel, itemRemote: FileDomainModel) {
        assertEquals(itemLocalModel.id, itemRemote.id)
        assertEquals(itemLocalModel.name, itemRemote.name)
        assertEquals(itemLocalModel.url, itemRemote.url)
        assertEquals(itemLocalModel.localPath, itemRemote.localPath)
        assertEquals(itemLocalModel.type, itemRemote.type)
    }


    @Test
    fun mapToDomainModel() {
        // Arrange
        val itemLocal= FilesFactory.generateFilesLocalModel()

        // Act
        val itemModel = itemLocal.mapToDomain()

        // Assert
        assertItemModelMapDataEqual(itemLocal, itemModel)
    }

    /**
     * Helpers Methods
     */
    private fun assertItemModelMapDataEqual(itemLocalModel: FileLocalModel, itemRemote: FileDomainModel) {
        assertEquals(itemLocalModel.id, itemRemote.id)
        assertEquals(itemLocalModel.name, itemRemote.name)
        assertEquals(itemLocalModel.url, itemRemote.url)
        assertEquals(itemLocalModel.localPath, itemRemote.localPath)
        assertEquals(itemLocalModel.type, itemRemote.type)
    }


}