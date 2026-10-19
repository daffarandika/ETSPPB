package com.itsrobocon.ets_ppb

import android.app.Application
import com.itsrobocon.ets_ppb.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LibraryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@LibraryApplication)
            modules(appModule)
        }
    }
}
