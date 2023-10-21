package com.darekbx.hexview

import android.app.Application
import com.darekbx.hexview.di.MiniDi

class HexViewApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MiniDi.context = this
    }
}