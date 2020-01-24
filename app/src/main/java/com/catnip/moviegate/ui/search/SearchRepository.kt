package com.catnip.moviegate.ui.search

import androidx.lifecycle.LiveData
import com.catnip.moviegate.data.datasource.search.SearchDataSource
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.ext.toLiveData
import com.catnip.moviegate.model.search.SearchResult
import io.reactivex.disposables.CompositeDisposable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class SearchRepository(
    val dataSource: SearchDataSource,
    val compositeDisposable: CompositeDisposable
) {
    val searchResult: LiveData<ResultState<SearchResult>> by lazy {
        dataSource.searchResult.toLiveData(compositeDisposable)
    }

    fun fetchSearchResult(query: String) {
        dataSource.fetchSearchResult(query)
    }

    fun onClear() {
        compositeDisposable.clear()
    }


}