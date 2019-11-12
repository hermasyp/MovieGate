package com.catnip.moviegate.model.detailtvshow


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailTvShow(
    @Json(name = "backdrop_path")
    var backdropPath: String,
    @Json(name = "created_by")
    var createdBy: List<CreatedBy>,
    @Json(name = "episode_run_time")
    var episodeRunTime: List<Int>,
    @Json(name = "first_air_date")
    var firstAirDate: String,
    @Json(name = "genres")
    var genres: List<Genre>,
    @Json(name = "homepage")
    var homepage: String,
    @Json(name = "id")
    var id: Int,
    @Json(name = "in_production")
    var inProduction: Boolean,
    @Json(name = "languages")
    var languages: List<String>,
    @Json(name = "last_air_date")
    var lastAirDate: String,
    @Json(name = "last_episode_to_air")
    var lastEpisodeToAir: LastEpisodeToAir,
    @Json(name = "name")
    var name: String,
    @Json(name = "networks")
    var networks: List<Network>,
    @Json(name = "next_episode_to_air")
    var nextEpisodeToAir: NextEpisodeToAir,
    @Json(name = "number_of_episodes")
    var numberOfEpisodes: Int,
    @Json(name = "number_of_seasons")
    var numberOfSeasons: Int,
    @Json(name = "origin_country")
    var originCountry: List<String>,
    @Json(name = "original_language")
    var originalLanguage: String,
    @Json(name = "original_name")
    var originalName: String,
    @Json(name = "overview")
    var overview: String,
    @Json(name = "popularity")
    var popularity: Double,
    @Json(name = "poster_path")
    var posterPath: String,
    @Json(name = "production_companies")
    var productionCompanies: List<ProductionCompany>,
    @Json(name = "seasons")
    var seasons: List<Season>,
    @Json(name = "status")
    var status: String,
    @Json(name = "type")
    var type: String,
    @Json(name = "vote_average")
    var voteAverage: Double,
    @Json(name = "vote_count")
    var voteCount: Int
)