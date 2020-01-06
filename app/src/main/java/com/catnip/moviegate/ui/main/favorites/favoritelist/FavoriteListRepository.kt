package com.catnip.moviegate.ui.main.favorites.favoritelist

import androidx.lifecycle.LiveData
import com.catnip.moviegate.data.local.dao.FavoriteDataSource
import com.catnip.moviegate.data.local.entity.Favorite
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.ext.toLiveData
import io.reactivex.disposables.CompositeDisposable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class FavoriteListRepository(
    private val dataSource: FavoriteDataSource,
    private val compositeDisposable: CompositeDisposable
) {
    val saveResult: LiveData<ResultState<Boolean>> by lazy {
        dataSource.saveResult.toLiveData(compositeDisposable)
    }
    val deleteResult: LiveData<ResultState<Boolean>> by lazy {
        dataSource.deleteResult.toLiveData(compositeDisposable)
    }
    val favoriteResult: LiveData<ResultState<MutableList<Favorite>>> by lazy {
        dataSource.favoriteResult.toLiveData(compositeDisposable)
    }

    fun saveFavorite(favorite: Favorite) {
        dataSource.saveFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) {
        dataSource.deleteFavorite(favorite)
    }

    fun getFavorite(contentType: String) {
        dataSource.getFavorites(contentType)
    }

    fun onCleared() {
        compositeDisposable.clear()
    }
}