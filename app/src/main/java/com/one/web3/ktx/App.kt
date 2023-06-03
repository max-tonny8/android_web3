package com.one.web3.ktx

import com.one.coreapp.App
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : App() {

    override fun onCreate() {

        startKoin {

            androidContext(this@App)

            androidLogger(Level.NONE)

            modules()
        }

        super.onCreate()
    }
}