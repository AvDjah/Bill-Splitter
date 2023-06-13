package com.example.billsplitter

import android.app.Application
import com.example.billsplitter.data.AppContainer
import com.example.billsplitter.data.AppDataContainer

class BillSplitterApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}