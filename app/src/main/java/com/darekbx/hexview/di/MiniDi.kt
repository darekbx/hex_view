package com.darekbx.hexview.di

import android.content.Context
import com.darekbx.hexview.file.FileReader
import com.darekbx.hexview.ui.hexview.HexViewModel

/**
 * Mini Dependecy Injection object
 */
object MiniDi {

    lateinit var context: Context

    val contentResolver
        get() = context.contentResolver

    val fileReader
        get() = FileReader(contentResolver)

    val hexViewModel
        get() = HexViewModel()

}
