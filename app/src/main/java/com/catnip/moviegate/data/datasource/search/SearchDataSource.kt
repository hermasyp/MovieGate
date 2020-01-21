package com.catnip.moviegate.data.datasource.search

import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.data.network.RetrofitApi
import com.catnip.moviegate.data.network.Scheduler
import com.catnip.moviegate.ext.*
import com.catnip.moviegate.model.common.Results
import com.catnip.moviegate.model.content.Content
import com.catnip.moviegate.model.search.SearchResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class SearchDataSource(
    private val api: RetrofitApi,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) {
    val contentResults: PublishSubject<ResultState<SearchResult>> =
        PublishSubject.create<ResultState<SearchResult>>()

    fun fetchSearchResult(query: String) {
        contentResults.loading(true)
        api.getSearchMovie(query)
            .zipWith<Results<Content>, SearchResult>(api.getSearchTv(query),
                BiFunction { resultMovie, resultTvShow -> SearchResult(resultMovie, resultTvShow) })
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    contentResults.success(it)
                },
                {
                    contentResults.failed(it)
                }
            ).addTo(compositeDisposable)
    }
}