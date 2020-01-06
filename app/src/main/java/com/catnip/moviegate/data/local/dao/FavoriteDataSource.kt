package com.catnip.moviegate.data.local.dao

import com.catnip.moviegate.data.local.entity.Favorite
import com.catnip.moviegate.data.network.AppScheduler
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.ext.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class FavoriteDataSource(
    private val favoriteDao: FavoriteDao,
    private val appScheduler: AppScheduler,
    private val compositeDisposable: CompositeDisposable
) {
    val saveResult: PublishSubject<ResultState<Boolean>> =
        PublishSubject.create<ResultState<Boolean>>()
    val deleteResult: PublishSubject<ResultState<Boolean>> =
        PublishSubject.create<ResultState<Boolean>>()
    val favoriteResult: PublishSubject<ResultState<MutableList<Favorite>>> =
        PublishSubject.create<ResultState<MutableList<Favorite>>>()


    fun saveFavorite(favorite: Favorite) {
        saveResult.loading(true)
        favoriteDao.insert(favorite)
            .performOnBackOutOnMain(appScheduler)
            .subscribe(
                {
                    saveResult.success(true)
                },
                {
                    saveResult.failed(it)
                }
            ).addTo(compositeDisposable)
    }

    fun deleteFavorite(favorite: Favorite) {
        deleteResult.loading(true)
        favoriteDao.delete(favorite)
            .performOnBackOutOnMain(appScheduler)
            .subscribe(
                {
                    deleteResult.success(true)
                },
                {
                    deleteResult.failed(it)
                }
            ).addTo(compositeDisposable)
    }

    fun getFavorites(contentType: String) {
        favoriteResult.loading(true)
        favoriteDao.getAll(contentType)
            .performOnBackOutOnMain(appScheduler)
            .subscribe(
                {
                    favoriteResult.success(it)
                },
                {
                    favoriteResult.failed(it)
                }
            ).addTo(compositeDisposable)
    }


}