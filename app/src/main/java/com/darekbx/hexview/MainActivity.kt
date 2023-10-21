package com.darekbx.hexview

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.darekbx.hexview.ui.NoPermissionGrantedView
import com.darekbx.hexview.ui.filepick.FilePickView
import com.darekbx.hexview.ui.hexview.HexViewScreen
import com.darekbx.hexview.ui.theme.HexViewTheme

class MainActivity : ComponentActivity() {

    companion object {
       private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
        )
    }

    private var permissionState = mutableStateOf(false)

    private val filesPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        when {
            permissionsGranted(this) -> permissionState.value = true
            else -> permissionState.value = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle back press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        setContent {
            val context = LocalContext.current
            HexViewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
                    if (permissionsGranted(context) || permissionState.value) {
                        selectedFileUri
                            ?.let { HexViewScreen(uri = it) }
                            ?: run { FilePickView { uri -> selectedFileUri = uri } }
                    } else {
                        NoPermissionGrantedView {
                            filesPermissionRequest.launch(REQUIRED_PERMISSIONS)
                        }
                    }
                }
            }
        }
    }

    private fun permissionsGranted(context: Context): Boolean {
        return REQUIRED_PERMISSIONS.all { isPermissionGranted(context, it) }
    }

    private fun isPermissionGranted(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
}
