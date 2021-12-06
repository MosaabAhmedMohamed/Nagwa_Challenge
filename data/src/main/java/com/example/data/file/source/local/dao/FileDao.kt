package com.example.data.file.source.local.dao

import androidx.room.*
import com.example.data.file.source.local.model.FileLocalModel
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFiles(files: List<FileLocalModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFile(file: FileLocalModel)

    @Query("select * from FileLocalModel")
    fun getFiles(): Flowable<List<FileLocalModel>>

    @Query("select * from FileLocalModel WHERE id = :fileId")
    fun getFile(fileId:Int): Single<FileLocalModel>

    @Query("delete from FileLocalModel")
    fun deleteAllFiles()

    @Transaction
    fun cacheFiles(files:List<FileLocalModel>) {
        deleteAllFiles()
        insertFiles(files)
    }

}