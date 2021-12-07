package com.example.presentation.base

import android.app.DownloadManager
import android.database.Cursor
import com.example.core.base.ext.intValue
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean
import java.lang.Exception


class DownloadStateRetriever(private val downloadManager: DownloadManager) {

    fun retrieve(id: Long, itemId_: Int): Observable<Pair<Int,Int>> {
        val downloading = AtomicBoolean(true)

        return Observable.fromCallable {
            val query = DownloadManager.Query().setFilterById(id)
            val cursor = downloadManager.query(query)
            cursor.moveToFirst()

            val bytesDownloaded = cursor.intValue(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            val bytesTotal = cursor.intValue(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)

            closeStreamOnSuccess(cursor, downloading)

            if (bytesTotal == 0) 0 else ((bytesDownloaded * 100F) / bytesTotal).toInt()
        }
            .subscribeOn(Schedulers.newThread())
            .repeatUntil { !downloading.get() }
            .flatMap { return@flatMap Observable.just(Pair(itemId_, it)) }
    }

    private fun closeStreamOnSuccess(
        cursor: Cursor,
        downloading: AtomicBoolean
    ) {
        if (isSuccessful(cursor)) {
            try {
                downloading.set(false)
                cursor.close()
            } catch (e: Exception) {
                downloading.set(false)
            } finally {
                cursor.close()
            }

        }
    }

    private fun isSuccessful(cursor: Cursor) = status(cursor) == DownloadManager.STATUS_SUCCESSFUL

    private fun status(cursor: Cursor) = cursor.intValue(DownloadManager.COLUMN_STATUS)


}