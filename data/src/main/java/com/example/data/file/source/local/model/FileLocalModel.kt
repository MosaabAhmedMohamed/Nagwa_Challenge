package com.example.data.file.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.core.model.DownloadStatus
import com.example.data.file.source.local.converter.FileModelConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "FileLocalModel")
data class FileLocalModel(


    @PrimaryKey
    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @Expose
    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String?,

    @Expose
    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String?,

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "localPath")
    var localPath: String?,

    @ColumnInfo(name = "DownloadStatus")
    @TypeConverters(FileModelConverter::class)
    var downloadStatus: DownloadStatus = DownloadStatus.NON
)
