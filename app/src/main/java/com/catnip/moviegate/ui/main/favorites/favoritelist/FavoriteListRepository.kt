package com.catnip.moviegate.ui.main.favorites.favoritelist

import androidx.lifecycle.LiveData
import com.catnip.moviegate.data.local.dao.FavoriteDataSource
import com.catnip.moviegate.data.local.entity.Favorite
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.ext.toLiveData

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class FavoriteListRepository(
    private val dataSource: FavoriteDataSource
) {
    val saveResult: LiveData<ResultState<Boolean>> by lazy {
        dataSource.saveResult.toLiveData(dataSource.compositeDisposable)
    }
    val deleteResult: LiveData<ResultState<Boolean>> by lazy {
        dataSource.deleteResult.toLiveData(dataSource.compositeDisposable)
    }
    val favoriteResult: LiveData<ResultState<MutableList<Favorite>>> by lazy {
        dataSource.favoriteResult.toLiveData(dataSource.compositeDisposable)
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
        dataSource.compositeDisposable.dispose()
    }
}