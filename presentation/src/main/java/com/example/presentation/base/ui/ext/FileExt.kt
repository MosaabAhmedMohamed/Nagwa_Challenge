package com.example.presentation.base.ui.ext

import android.net.Uri
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File

fun Fragment.createFolderAndGetPath(): String {
    val folderPath = (requireActivity().getExternalFilesDir(null)?.absolutePath
        .toString() + File.separator + "Tutorials" + File.separator)

    if (!File(folderPath).exists()) {
        File(folderPath).mkdir();
    }
    return folderPath
}

fun Fragment.getLocalFileUri(localPath: String): Uri {
    val file = File(localPath)
    return FileProvider.getUriForFile(requireActivity(), "${requireActivity().packageName}.provider", file)
}