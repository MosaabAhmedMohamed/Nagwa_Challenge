package com.example.data.file.source.remote

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import javax.inject.Inject

class FileDownloadDataSource @Inject constructor(private val context: Context) {

    fun download(
        folderPath: String,
        downloadUrl: String,
        name: String?
    ): Pair<DownloadManager?, Long?> {
        val downloadUri = Uri.parse(downloadUrl.trim())
        val mFilePath = "file://${folderPath}/${name}"
        return try {
            val req = DownloadManager.Request(downloadUri);
            req.setDestinationUri(Uri.parse(mFilePath));
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val opId = dm.enqueue(req);
            Pair(dm, opId)
        } catch (e: IllegalArgumentException) {
            Pair(null, null)
        }
    }


}