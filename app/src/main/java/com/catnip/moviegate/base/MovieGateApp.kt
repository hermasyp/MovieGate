package com.catnip.moviegate.base

import android.app.Application
import android.content.Context
import com.catnip.moviegate.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MovieGateApp : Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        startKoin {
            androidContext(this@MovieGateApp)
            modules(listOf(databaseModule,networkModule,scopesModule))
        }
    }
}