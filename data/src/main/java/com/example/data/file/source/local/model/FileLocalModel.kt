package com.example.data.file.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FileLocalModel")
data class FileLocalModel(


    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = 0,


    @ColumnInfo(name = "type")
    val type: String?,


    @ColumnInfo(name = "url")
    val url: String?,


    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "localPath")
    var localPath: String?,

    @ColumnInfo(name = "isDownloaded")
    var isDownloaded: Boolean = false,
)
