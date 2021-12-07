package com.example.presentation.base.ui.ext


import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Fragment.isStoragePermissionGranted(): Boolean {
    val read_permission =
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
    return read_permission == PackageManager.PERMISSION_GRANTED

}


fun Fragment.askForFilePermission() {
    ActivityCompat.requestPermissions(
        requireActivity(), arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),
        FILE_ACCESS_PERMISSION
    )
}

const val FILE_ACCESS_PERMISSION = 2222