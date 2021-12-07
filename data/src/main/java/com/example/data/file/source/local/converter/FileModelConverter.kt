package com.example.data.file.source.local.converter

import androidx.room.TypeConverter
import com.example.core.model.DownloadStatus

class FileModelConverter {

    @TypeConverter
    fun getDownloadStatus(string: String?): DownloadStatus {
        return try {
            string?.let { DownloadStatus.valueOf(string) } ?: DownloadStatus.NON
        } catch (ex: Exception) {
            DownloadStatus.NON
        }
    }

    @TypeConverter
    fun downloadStatusString(accountStatus: DownloadStatus?): String {
        return accountStatus?.name ?: DownloadStatus.NON.name
    }
}