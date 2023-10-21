package com.darekbx.hexview.ui.hexview

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darekbx.hexview.di.MiniDi
import com.darekbx.hexview.file.FileReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HexViewModel constructor(
    private val fileReader: FileReader = MiniDi.fileReader
) : ViewModel() {

    val data = mutableStateListOf<Byte>()
    val size = mutableStateOf(0)

    suspend fun openFile(fileUri: Uri) {
        withContext(Dispatchers.IO) {
            size.value = fileReader.openFile(fileUri)
        }
    }

    fun read(partSize: Int = 128) {
        viewModelScope.launch {
            val contents = fileReader.read(partSize)
            data.addAll(contents)
        }
    }

    private fun close() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                fileReader.close()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        close()
    }
}
