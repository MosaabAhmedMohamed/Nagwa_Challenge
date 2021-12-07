package com.example.presentation.file.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.R
import com.example.presentation.base.ui.BaseViewHolder
import com.example.presentation.databinding.ItemPdfDownloadingBinding
import com.example.presentation.file.model.FileUiModel

class PDFDownloadingViewHolder(private val binding: ItemPdfDownloadingBinding) :
    BaseViewHolder<FileUiModel?>(binding.root) {


    override fun onBind(item: FileUiModel?) {
        binding.tvTitle.text = item?.name
    }


    companion object {
        fun create(parent: ViewGroup): PDFDownloadingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pdf_downloading, parent, false)

            val binding = ItemPdfDownloadingBinding.bind(view)

            return PDFDownloadingViewHolder(
                binding
            )
        }
    }
}