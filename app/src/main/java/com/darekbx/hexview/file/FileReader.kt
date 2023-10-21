package com.darekbx.hexview.file

import android.content.ContentResolver
import android.net.Uri
import com.darekbx.hexview.di.MiniDi
import java.io.IOException
import java.io.InputStream

class FileReader constructor(
    private val contentResolver: ContentResolver = MiniDi.contentResolver
) {

    private lateinit var inputStream: InputStream

    /**
     * @return File size
     */
    fun openFile(fileUri: Uri): Int {
        var size = 0
        contentResolver.openInputStream(fileUri)?.let {
            inputStream = it
            size = it.available()
        }
        return size
    }

    fun read(partSize: Int = 128): List<Byte> {
        val buffer = ByteArray(partSize)
        val bytesRead = inputStream.read(buffer)
        return buffer.copyOf(bytesRead).toList()
    }

    fun close() {
        try {
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}