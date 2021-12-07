package com.example.presentation.file.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.R
import com.example.presentation.base.ui.BaseViewHolder
import com.example.presentation.databinding.ItemPdfDownloadBinding
import com.example.presentation.file.model.FileUiModel

class PDFDownloadViewHolder(private val binding: ItemPdfDownloadBinding) :
    BaseViewHolder<FileUiModel?>(binding.root) {

    override fun onBind(item: FileUiModel?) {
        binding.tvTitle.text = item?.name
    }

    companion object {
        fun create(parent: ViewGroup): PDFDownloadViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pdf_download, parent, false)

            val binding = ItemPdfDownloadBinding.bind(view)

            return PDFDownloadViewHolder(
                binding
            )
        }
    }
}