package com.example.presentation.file.mapping

import com.example.domain.file.model.FileDomainModel
import com.example.presentation.factory.FileFactory
import com.example.presentation.file.model.FileType
import com.example.presentation.file.model.FileUiModel
import org.junit.Assert
import org.junit.Test

class FileMapping {

    @Test
    fun mapFileToUiModel() {
        // Arrange
        val itemDomain= FileFactory.generateFileDomainModel()

        // Act
        val itemUIModel = itemDomain.mapToUIModel(FileType.PDF)

        // Assert
        assertFileModelMapDataEqual(itemDomain, itemUIModel)
    }

    /**
     * Helpers Methods
     */
    private fun assertFileModelMapDataEqual(itemDomain: FileDomainModel, itemUIModel: FileUiModel) {
        Assert.assertEquals(itemDomain.id, itemUIModel.id)
        Assert.assertEquals(itemDomain.name, itemUIModel.name)
        Assert.assertEquals(itemDomain.url, itemUIModel.url)
        Assert.assertEquals(itemDomain.localPath, itemUIModel.localPath)
    }


}