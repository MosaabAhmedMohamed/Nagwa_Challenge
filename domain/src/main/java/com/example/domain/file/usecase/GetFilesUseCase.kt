package com.example.domain.file.usecase

import com.example.domain.file.model.FileDomainModel
import com.example.domain.file.repository.FileRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class GetFilesUseCase @Inject constructor(private val fileRepository: FileRepository) {

    fun getFiles(): Flowable<List<FileDomainModel>> {
        return fileRepository.getFiles()
    }

    fun getFile(id: Int): Single<FileDomainModel> {
        return fileRepository.getFile(id)
    }


}