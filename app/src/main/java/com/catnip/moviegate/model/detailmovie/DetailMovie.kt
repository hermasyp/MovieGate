package com.catnip.moviegate.model.detailmovie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailMovie(
    @Json(name = "adult")
    var adult: Boolean,
    @Json(name = "backdrop_path")
    var backdropPath: String,
    @Json(name = "belongs_to_collection")
    var belongsToCollection: Any,
    @Json(name = "budget")
    var budget: Int,
    @Json(name = "genres")
    var genres: List<Genre>,
    @Json(name = "homepage")
    var homepage: String,
    @Json(name = "id")
    var id: Int,
    @Json(name = "imdb_id")
    var imdbId: String,
    @Json(name = "original_language")
    var originalLanguage: String,
    @Json(name = "original_title")
    var originalTitle: String,
    @Json(name = "overview")
    var overview: String,
    @Json(name = "popularity")
    var popularity: Double,
    @Json(name = "poster_path")
    var posterPath: String,
    @Json(name = "production_companies")
    var productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries")
    var productionCountries: List<ProductionCountry>,
    @Json(name = "release_date")
    var releaseDate: String,
    @Json(name = "revenue")
    var revenue: Int,
    @Json(name = "runtime")
    var runtime: Int,
    @Json(name = "spoken_languages")
    var spokenLanguages: List<SpokenLanguage>,
    @Json(name = "status")
    var status: String,
    @Json(name = "tagline")
    var tagline: String,
    @Json(name = "title")
    var title: String,
    @Json(name = "video")
    var video: Boolean,
    @Json(name = "vote_average")
    var voteAverage: Double,
    @Json(name = "vote_count")
    var voteCount: Int
)