package com.android.topheadlinesapp

import android.app.Application
import com.tha.core.Constants.LIB_NAME_APP_NATIVE_DATA
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class THApp: Application() {
    companion object {
        init {
            System.loadLibrary(LIB_NAME_APP_NATIVE_DATA)
        }
    }
}