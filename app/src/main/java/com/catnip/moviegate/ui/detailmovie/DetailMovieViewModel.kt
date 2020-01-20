package com.catnip.moviegate.ui.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.catnip.moviegate.model.detailmovie.DetailMovie
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.ui.main.favorites.favoritelist.FavoriteListRepository

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class DetailMovieViewModel(private val movieRepository: DetailMovieRepository,
                           private val favoriteListRepository: FavoriteListRepository) : ViewModel() {



    val detailMovie : LiveData<ResultState<DetailMovie>> by lazy {
        movieRepository.detailMovieResult
    }
    val setToFavorite: LiveData<ResultState<Boolean>> by lazy {
        favoriteListRepository.saveResult
    }
    val deleteFavorite: LiveData<ResultState<Boolean>> by lazy {
        favoriteListRepository.deleteResult
    }
    val isFavorited: LiveData<ResultState<Boolean>> by lazy {
        favoriteListRepository.isFavoritedResult
    }

    fun setToFavorite(detailMovie: DetailMovie){
        favoriteListRepository.saveFavorite(detailMovie.detailToFavorite())
    }
    fun deleteFromFavorite(detailMovie: DetailMovie){
        favoriteListRepository.deleteFavorite(detailMovie.detailToFavorite())
    }
    fun isFavorited(id: String){
        favoriteListRepository.isFavorited(id)
    }

    fun loadDetailMovie(idMovie : String){
        movieRepository.fetchDetail(idMovie)
    }

    override fun onCleared() {
        super.onCleared()
        favoriteListRepository.onCleared()
        movieRepository.onCleared()
    }
}