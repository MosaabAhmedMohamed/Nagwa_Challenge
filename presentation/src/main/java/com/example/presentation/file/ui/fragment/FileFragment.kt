package com.example.presentation.file.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.presentation.base.ViewModelFactory
import com.example.presentation.base.ui.BaseFragment
import com.example.presentation.base.ui.ext.*
import com.example.presentation.databinding.FragmentFileBinding
import com.example.presentation.file.model.FileType
import com.example.presentation.file.model.FileUiModel
import com.example.presentation.file.ui.adapter.FilesAdapter
import com.example.presentation.file.viewmodel.FileViewModel
import com.example.presentation.file.viewstate.FilesViewState
import javax.inject.Inject

class FileFragment : BaseFragment() {

    private lateinit var binding: FragmentFileBinding

    private val itemListAdapter by lazy {
        FilesAdapter({
            onIemDownloadClickAction(it)
        }, {
            onIemOpenClickAction(it)
        })
    }


    @Inject
    lateinit var fileViewModelFactory: ViewModelFactory<FileViewModel>
    private val fileViewModel by lazy {
        ViewModelProvider(this, fileViewModelFactory)
            .get(FileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun init() {
        initRefresh()
        observeViewState()
        initItemsRv()
        getItems()
    }

    private fun initRefresh() {
        binding.refreshSrl.init {
            refreshItems()
        }
    }

    private fun observeViewState() {
        fileViewModel.filesViewStateLD.observe(this, {
            handleViewState(it)
        })
    }

    private fun handleViewState(viewState: FilesViewState) {
        when (viewState) {
            FilesViewState.Loading -> loadingState()
            is FilesViewState.onError -> errorState(viewState.error)
            is FilesViewState.onSuccess -> onItemsLoaded(viewState.result)
        }
    }

    private fun errorState(error: Throwable? = null) {
        binding.errMessageRootView.visible()
        showItemsViews(false)
        binding.refreshSrl.stopRefresh()
        binding.progressRootView.gone()
    }

    private fun loadingState() {
        showItemsViews(false)
        binding.progressRootView.visible()
        binding.errMessageRootView.gone()
    }

    private fun onItemsLoaded(result: List<FileUiModel>) {
        showItemsViews(true)
        binding.refreshSrl.stopRefresh()
        binding.progressRootView.gone()
        binding.errMessageRootView.gone()
        itemListAdapter.setData(result.toMutableList())
    }

    private fun initItemsRv() {
        binding.listRv.layoutManager = GridLayoutManager(requireContext(),2)
        binding.listRv.adapter = itemListAdapter
    }


    private fun onIemOpenClickAction(item: FileUiModel) {
        item.localPath?.let {
            when (item.type) {
                FileType.PDF -> requireContext().openPDFIntent(getLocalFileUri(it))
                else -> requireContext().openVideoIntent(getLocalFileUri(it))
            }
        }
    }

    private fun onIemDownloadClickAction(item: FileUiModel) {
        fileViewModel.fileSelectedItem = item
        checkPermission(item)
    }

    private fun checkPermission(item: FileUiModel) {
        if (isStoragePermissionGranted()) {
            item.url?.let {
                download(it, item.name, item.id)
            }
        } else {
            askForFilePermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == FILE_ACCESS_PERMISSION && isStoragePermissionGranted()) {
            retryDownload()
        }
    }

    private fun retryDownload() {
        fileViewModel.fileSelectedItem?.let { tutorialUIModel ->
            tutorialUIModel.url?.let {
                download(it, tutorialUIModel.name, tutorialUIModel.id)
            }
        }
    }

    private fun download(downloadUrl: String, name: String?, itemId: Int?) {
        if (downloadUrl.isNotEmpty()) {
            fileViewModel.downloadFile(createFolderAndGetPath(), downloadUrl, name, itemId)
        }
    }


    private fun getItems() {
        fileViewModel.getFiles()
    }

    private fun refreshItems() {
        fileViewModel.getFiles(true)
    }

    private fun showItemsViews(show: Boolean) {
        binding.listRv.visibility(show)
    }

    override fun onViewClicked() {
        super.onViewClicked()
        binding.errMessageRootView.setOnClickListener {
            refreshItems()
        }
    }

}