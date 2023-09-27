package com.ikvakan.tumblrdemo

import android.app.Application
import com.ikvakan.tumblrdemo.di.network.networkModule
import com.ikvakan.tumblrdemo.di.remote.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TumblrApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.LOGS) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@TumblrApp)
            //  add modules
            modules(networkModule, repositoryModule)
        }
    }
}
