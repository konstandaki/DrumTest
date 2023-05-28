package com.konstandaki.drumtest

import android.app.Application
import com.konstandaki.drumtest.data.AppContainer
import com.konstandaki.drumtest.data.DefaultAppContainer

class SportsApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}