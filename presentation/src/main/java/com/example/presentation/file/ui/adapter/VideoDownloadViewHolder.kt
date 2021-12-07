package com.example.presentation.file.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.R
import com.example.presentation.base.ui.BaseViewHolder
import com.example.presentation.databinding.ItemVideoDownloadBinding
import com.example.presentation.file.model.FileUiModel

class VideoDownloadViewHolder(private val binding : ItemVideoDownloadBinding)
    : BaseViewHolder<FileUiModel?>(binding.root) {

    override fun onBind(item: FileUiModel?) {
        binding.tvTitle.text = item?.name
    }


    companion object {
        fun create(parent: ViewGroup): VideoDownloadViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video_download, parent, false)

            val binding = ItemVideoDownloadBinding.bind(view)

            return VideoDownloadViewHolder(
                binding
            )
        }
    }
}