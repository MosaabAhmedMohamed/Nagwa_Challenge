package com.example.data.file.source.local.converter

import androidx.room.TypeConverter
import com.example.core.model.DownloadStatus

class FileModelConverter {

    @TypeConverter
    fun getDownloadStatus(string: String?): DownloadStatus? {
        return try {
            if (string != null)
                DownloadStatus.valueOf(string)
            else null
        } catch (ex: Exception) {
            null
        }
    }

    @TypeConverter
    fun downloadStatusString(accountStatus: DownloadStatus?): String? {
        return accountStatus?.name
    }
}