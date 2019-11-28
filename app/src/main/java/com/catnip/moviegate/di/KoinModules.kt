package com.catnip.moviegate.di

import com.catnip.moviegate.datasource.MoviesDataSourceFactory
import com.catnip.moviegate.di.ScopeNames.MoviesListScopes
import com.catnip.moviegate.network.AppScheduler
import com.catnip.moviegate.network.RetrofitApi
import com.catnip.moviegate.network.Scheduler
import com.catnip.moviegate.ui.main.movie.MoviesRepository
import com.catnip.moviegate.ui.main.movie.MoviesViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

object ScopeNames{
    const val MoviesListScopes = "MoviesFragment"
}

val networkModule = module {
    single { RetrofitApi() }
    single { AppScheduler() }
}

val mainScopesModule = module {
    scope(named(MoviesListScopes)) {
        scoped { CompositeDisposable() }
        scoped { MoviesRepository(get()) }
        scoped { MoviesDataSourceFactory(get(), get(), get()) }
        viewModel { MoviesViewModel(get(), get()) }
    }
}
