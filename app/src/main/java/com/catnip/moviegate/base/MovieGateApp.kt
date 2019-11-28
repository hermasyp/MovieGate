package com.catnip.moviegate.base

import android.app.Application
import com.catnip.moviegate.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MovieGateApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieGateApp)
            modules(listOf(networkModule,mainScopesModule))
        }
    }
}