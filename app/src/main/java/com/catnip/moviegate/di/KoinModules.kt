package com.catnip.moviegate.di

import com.catnip.moviegate.datasource.contents.ContentsDataSourceFactory
import com.catnip.moviegate.datasource.detailmovies.DetailMovieDataSource
import com.catnip.moviegate.datasource.detailmovies.DetailTVShowDataSource
import com.catnip.moviegate.datasource.movies.MoviesDataSourceFactory
import com.catnip.moviegate.datasource.tvshows.TvShowsDataSourceFactory
import com.catnip.moviegate.di.ScopeNames.DetailMovieScopes
import com.catnip.moviegate.di.ScopeNames.DetailTvShowScopes
import com.catnip.moviegate.di.ScopeNames.MoviesListScopes
import com.catnip.moviegate.di.ScopeNames.TvShowsListScopes
import com.catnip.moviegate.network.AppScheduler
import com.catnip.moviegate.network.RetrofitApi
import com.catnip.moviegate.ui.detailmovie.DetailMovieRepository
import com.catnip.moviegate.ui.detailmovie.DetailMovieViewModel
import com.catnip.moviegate.ui.detailtvshow.DetailTvShowRepository
import com.catnip.moviegate.ui.detailtvshow.DetailTvShowViewModel
import com.catnip.moviegate.ui.main.movie.MoviesRepository
import com.catnip.moviegate.ui.main.movie.MoviesViewModel
import com.catnip.moviegate.ui.main.tvshow.TvShowsRepository
import com.catnip.moviegate.ui.main.tvshow.TvShowsViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

object ScopeNames {
    const val MoviesListScopes = "MoviesFragment"
    const val TvShowsListScopes = "TvShowsFragment"
    const val DetailMovieScopes = "DetailMovieActivity"
    const val DetailTvShowScopes = "DetailTvShowActivity"
}

val networkModule = module {
    single { RetrofitApi() }
    single { AppScheduler() }

}

val scopesModule = module {
    scope(named(MoviesListScopes)) {
        scoped { CompositeDisposable() }
        scoped { MoviesRepository(get()) }
        scoped {
            MoviesDataSourceFactory(
                get(),
                get(),
                get()
            )
        }
        viewModel { MoviesViewModel(get(), get()) }
    }
    scope(named(TvShowsListScopes)) {
        scoped { CompositeDisposable() }
        scoped { TvShowsRepository(get()) }
        scoped {
            TvShowsDataSourceFactory(
                get(),
                get(),
                get()
            )
        }
        viewModel { TvShowsViewModel(get(), get()) }
    }

    scope(named(DetailMovieScopes)) {
        scoped { CompositeDisposable() }
        scoped { DetailMovieRepository(get(), get()) }
        scoped {
            DetailMovieDataSource(
                get(),
                get(),
                get()
            )
        }
        viewModel { DetailMovieViewModel(get()) }
    }
    scope(named(DetailTvShowScopes)) {
        scoped { CompositeDisposable() }
        scoped { DetailTvShowRepository(get(), get()) }
        scoped {
            DetailTVShowDataSource(
                get(),
                get(),
                get()
            )
        }
        viewModel { DetailTvShowViewModel(get()) }
    }
}
