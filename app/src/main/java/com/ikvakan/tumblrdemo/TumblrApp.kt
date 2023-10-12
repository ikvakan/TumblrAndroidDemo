package com.ikvakan.tumblrdemo

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresExtension
import com.ikvakan.tumblrdemo.di.app.viewModelModule
import com.ikvakan.tumblrdemo.di.domain.domainModule
import com.ikvakan.tumblrdemo.di.exception.exceptionModule
import com.ikvakan.tumblrdemo.di.local.databaseModule
import com.ikvakan.tumblrdemo.di.local.localRepositoryModule
import com.ikvakan.tumblrdemo.di.remote.connectivityModule
import com.ikvakan.tumblrdemo.di.remote.networkModule
import com.ikvakan.tumblrdemo.di.remote.remoteRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TumblrApp : Application() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.LOGS) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@TumblrApp)
            //  add modules
            modules(
                networkModule,
                remoteRepositoryModule,
                localRepositoryModule,
                viewModelModule,
                domainModule,
                exceptionModule,
                connectivityModule,
                databaseModule
            )
        }
    }
}
