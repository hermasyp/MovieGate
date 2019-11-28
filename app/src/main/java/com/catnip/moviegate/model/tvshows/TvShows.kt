package com.catnip.moviegate.model.tvshows


import com.google.gson.annotations.SerializedName

data class TvShows(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<TvShow>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)