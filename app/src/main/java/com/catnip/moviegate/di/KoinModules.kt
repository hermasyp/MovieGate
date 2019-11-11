package com.catnip.moviegate.di

import com.catnip.moviegate.network.RetrofitApi
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

val networkModule = module {
    single { RetrofitApi() }
}