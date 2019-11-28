package com.catnip.moviegate.model.movies


import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var movies: List<Movie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)