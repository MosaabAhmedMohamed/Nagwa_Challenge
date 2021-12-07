package com.example.presentation.file.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.R
import com.example.presentation.base.ui.BaseViewHolder
import com.example.presentation.databinding.ItemVideoDownloadingBinding
import com.example.presentation.file.model.FileUiModel

class VideoDownloadingViewHolder(private val binding: ItemVideoDownloadingBinding) :
    BaseViewHolder<FileUiModel?>(binding.root) {


    override fun onBind(item: FileUiModel?) {
        binding.tvTitle.text = item?.name
    }


    companion object {
        fun create(parent: ViewGroup): VideoDownloadingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video_downloading, parent, false)

            val binding = ItemVideoDownloadingBinding.bind(view)

            return VideoDownloadingViewHolder(
                binding
            )
        }
    }
}