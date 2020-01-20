package com.catnip.moviegate.ui.main.favorites.favoritelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.catnip.moviegate.data.local.entity.Favorite
import com.catnip.moviegate.data.network.ResultState

class FavoriteListViewModel(private val repository: FavoriteListRepository) : ViewModel() {

    val saveResult : LiveData<ResultState<Boolean>> by lazy {
        repository.saveResult
    }
    val deleteResult : LiveData<ResultState<Boolean>> by lazy {
        repository.deleteResult
    }
    val favoriteResult : LiveData<ResultState<MutableList<Favorite>>> by lazy {
        repository.favoriteResult
    }

    fun saveFavorite(favorite: Favorite) {
        repository.saveFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) {
        repository.deleteFavorite(favorite)
    }

    fun getFavorite(contentType: String) {
        repository.getFavorite(contentType)
    }

    override fun onCleared() {
        super.onCleared()
        repository.onCleared()
    }
}