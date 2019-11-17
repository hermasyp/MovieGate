package com.catnip.moviegate.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.catnip.moviegate.model.movies.Movie
import com.catnip.moviegate.network.PaginateResultState
import com.catnip.moviegate.network.ResultState
import io.reactivex.disposables.CompositeDisposable

class MoviesViewModel(private val repository: MoviesRepository,
                      private val compositeDisposable: CompositeDisposable) : ViewModel() {

    val movies : LiveData<PagedList<Movie>> by lazy {
        repository.fetchMovies()
    }

    val resultState : LiveData<PaginateResultState> by lazy {
        repository.getFetchState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}