package com.example.data.file.source.local

import android.content.Context
import com.example.data.file.source.local.dao.FileDao
import com.example.data.file.source.local.model.FileLocalModel
import com.example.data.file.source.mapping.mapToDomain
import com.example.domain.file.model.FileDomainModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.nio.charset.Charset
import javax.inject.Inject

class FileLocalDataSource @Inject constructor(
    private val context: Context,
    private val fileDao: FileDao,
    private val gson: Gson
) {

    fun getFiles(): Flowable<List<FileDomainModel>> {
        return fileDao.getFiles()
            .flatMap {
                if (it.isEmpty()) {
                    handleLoadingFromFileAndCaching()
                }
                Flowable.just(it.mapToDomain())
            }
    }

    private fun handleLoadingFromFileAndCaching() {
        handleJsonString().flatMap {
            cacheFiles(mapJsonStringToListOfFile(it))
            Observable.just(true)
        }.subscribe()
    }

    fun cacheFiles(files: List<FileLocalModel>) {
        fileDao.deleteAllFiles().subscribe()
        fileDao.insertFiles(files).subscribe()
    }

    private fun loadFromFile(): ByteArray {
        val inputStream: InputStream = context.assets.open("getListOfFilesResponse.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return buffer
    }

    private fun handleJsonString(): Observable<String> {
        return try {
            val buffer = loadFromFile()
            val jsonStr = String(buffer, Charset.forName("UTF-8"))
            Observable.just(jsonStr)
        } catch (e: IOException) {
            Observable.error(e)
        }
    }

    private fun mapJsonStringToListOfFile(jsonString: String): List<FileLocalModel> {
        val listOfFilesType: Type = object : TypeToken<List<FileLocalModel?>>() {}.type
        return gson.fromJson(jsonString, listOfFilesType)
    }

    fun updateFileInLocal(file: FileLocalModel) {
        fileDao.insertFile(file)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getFile(id: Int): Single<FileDomainModel> {
        return fileDao.getFile(id).map {
            it.mapToDomain()
        }
    }
}