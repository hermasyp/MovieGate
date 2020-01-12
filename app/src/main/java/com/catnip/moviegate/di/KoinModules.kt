package com.catnip.moviegate.di

import androidx.room.Room
import com.catnip.moviegate.data.datasource.detailmovies.DetailMovieDataSource
import com.catnip.moviegate.data.datasource.detailmovies.DetailTVShowDataSource
import com.catnip.moviegate.data.datasource.movies.MoviesDataSourceFactory
import com.catnip.moviegate.data.datasource.tvshows.TvShowsDataSourceFactory
import com.catnip.moviegate.data.local.dao.FavoriteDataSource
import com.catnip.moviegate.data.local.database.AppDatabase
import com.catnip.moviegate.di.ScopeNames.DetailMovieScopes
import com.catnip.moviegate.di.ScopeNames.DetailTvShowScopes
import com.catnip.moviegate.di.ScopeNames.MoviesListScopes
import com.catnip.moviegate.di.ScopeNames.TvShowsListScopes
import com.catnip.moviegate.data.network.AppScheduler
import com.catnip.moviegate.data.network.RetrofitApi
import com.catnip.moviegate.di.ScopeNames.FavoriteListScope
import com.catnip.moviegate.ui.detailmovie.DetailMovieRepository
import com.catnip.moviegate.ui.detailmovie.DetailMovieViewModel
import com.catnip.moviegate.ui.detailtvshow.DetailTvShowRepository
import com.catnip.moviegate.ui.detailtvshow.DetailTvShowViewModel
import com.catnip.moviegate.ui.main.favorites.favoritelist.FavoriteListRepository
import com.catnip.moviegate.ui.main.favorites.favoritelist.FavoriteListViewModel
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
    const val FavoriteListScope = "FavoriteListFragment"
    const val MoviesListScopes = "MoviesFragment"
    const val TvShowsListScopes = "TvShowsFragment"
    const val DetailMovieScopes = "DetailMovieActivity"
    const val DetailTvShowScopes = "DetailTvShowActivity"
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, AppDatabase.DBNAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    single { get<AppDatabase>().favoriteDao() }
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

    scope(named(FavoriteListScope)) {
        scoped { CompositeDisposable() }
        scoped { FavoriteListRepository(get(),get()) }
        scoped {
            FavoriteDataSource(
                get(),
                get(),
                get()
            )
        }
        viewModel { FavoriteListViewModel(get()) }
    }

}
