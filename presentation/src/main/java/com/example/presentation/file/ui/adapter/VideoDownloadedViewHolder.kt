package com.example.presentation.file.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.R
import com.example.presentation.base.ui.BaseViewHolder
import com.example.presentation.databinding.ItemVideoDownloadedBinding
import com.example.presentation.file.model.FileUiModel


class VideoDownloadedViewHolder(private val binding : ItemVideoDownloadedBinding)
    : BaseViewHolder<FileUiModel?>(binding.root) {

    override fun onBind(item: FileUiModel?) {
        binding.tvTitle.text = item?.name
    }

    companion object {
        fun create(parent: ViewGroup): VideoDownloadedViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video_downloaded, parent, false)

            val binding = ItemVideoDownloadedBinding.bind(view)

            return VideoDownloadedViewHolder(
                binding
            )
        }
    }
}