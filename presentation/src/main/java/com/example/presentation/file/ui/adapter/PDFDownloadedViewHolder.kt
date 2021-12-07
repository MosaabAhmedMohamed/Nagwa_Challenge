package com.example.presentation.file.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.presentation.R
import com.example.presentation.base.ui.BaseViewHolder
import com.example.presentation.databinding.ItemPdfDownloadedBinding
import com.example.presentation.file.model.FileUiModel

class PDFDownloadedViewHolder(private val binding: ItemPdfDownloadedBinding) :
    BaseViewHolder<FileUiModel?>(binding.root) {


    override fun onBind(item: FileUiModel?) {
        binding.tvTitle.text = item?.name
    }


    companion object {
        fun create(parent: ViewGroup): PDFDownloadedViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pdf_downloaded, parent, false)

            val binding = ItemPdfDownloadedBinding.bind(view)

            return PDFDownloadedViewHolder(
                binding
            )
        }
    }
}