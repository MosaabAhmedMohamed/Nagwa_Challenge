package com.example.presentation.file.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.DownloadStatus
import com.example.presentation.base.ui.BaseViewHolder
import com.example.presentation.file.model.FileType
import com.example.presentation.file.model.FileUiModel

class FilesAdapter(
    private val downloadClickAction: (item: FileUiModel) -> Unit,
    private val openClickAction: (item: FileUiModel) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tutorials: List<FileUiModel?> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PDF_DOWNLOADED_VIEW -> PDFDownloadedViewHolder.create(parent)
            PDF_DOWNLOAD_VIEW -> PDFDownloadViewHolder.create(parent)
            PDF_DOWNLOADING_VIEW -> PDFDownloadingViewHolder.create(parent)
            VIDEO_DOWNLOAD_VIEW -> VideoDownloadViewHolder.create(parent)
            VIDEO_DOWNLOADING_VIEW -> VideoDownloadingViewHolder.create(parent)
            else -> VideoDownloadedViewHolder.create(parent)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = tutorials[position]
        handleBinding(holder, item)
        handleItemClick(holder, position)
    }

    private fun handleItemClick(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (getItemViewType(position)) {
            PDF_DOWNLOAD_VIEW, VIDEO_DOWNLOAD_VIEW, VIDEO_DOWNLOADING_VIEW -> {
                holder.itemView.setOnClickListener {
                    tutorials[position]?.let {
                        downloadClickAction.invoke(it)
                    }
                }
            }
            else -> {
                holder.itemView.setOnClickListener {
                    tutorials[position]?.let {
                        openClickAction.invoke(it)
                    }
                }
            }
        }

    }

    private fun handleBinding(
        holder: RecyclerView.ViewHolder,
        item: FileUiModel?
    ) {
            (holder as BaseViewHolder<FileUiModel>).onBind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(result: List<FileUiModel>) {
        tutorials = result
        notifyDataSetChanged()
    }

    override fun getItemCount() = tutorials.size ?: 0

    override fun getItemViewType(position: Int): Int {
        val item = tutorials[position]
        return when (item?.type) {
            FileType.PDF -> getPDFViewType(item.downloadStatus)
            else -> getVIDEOViewType(item?.downloadStatus?:DownloadStatus.NON)
        }
    }

    private fun getPDFViewType(downloadStatus: DownloadStatus) =
        when(downloadStatus){
            DownloadStatus.NON -> PDF_DOWNLOAD_VIEW
            DownloadStatus.DOWNLOADING -> PDF_DOWNLOADING_VIEW
            DownloadStatus.PENDING -> PDF_DOWNLOAD_VIEW
            DownloadStatus.DOWNLOADED -> PDF_DOWNLOADED_VIEW
        }

    private fun getVIDEOViewType(downloadStatus: DownloadStatus) =
        when(downloadStatus){
            DownloadStatus.NON -> VIDEO_DOWNLOAD_VIEW
            DownloadStatus.DOWNLOADING -> VIDEO_DOWNLOADING_VIEW
            DownloadStatus.PENDING -> VIDEO_DOWNLOAD_VIEW
            DownloadStatus.DOWNLOADED -> VIDEO_DOWNLOADED_VIEW
        }


    companion object {
        const val PDF_DOWNLOADED_VIEW = 1
        const val PDF_DOWNLOAD_VIEW = 2
        const val PDF_DOWNLOADING_VIEW = 3
        const val VIDEO_DOWNLOADED_VIEW = 4
        const val VIDEO_DOWNLOAD_VIEW = 5
        const val VIDEO_DOWNLOADING_VIEW = 6
    }


}