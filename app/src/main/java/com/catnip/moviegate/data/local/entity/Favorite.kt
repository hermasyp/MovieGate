package com.catnip.moviegate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey
    var id: String,
    var type: ContentType,
    var title: String,
    var originalTitle: String,
    var releaseDate: String,
    var backdropPath: String,
    var genreIds: List<Int>,
    var originalLanguage: String,
    var overview: String,
    var popularity: Double,
    var posterPath: String,
    var voteAverage: Double,
    var voteCount: Int
)
enum class ContentType{
    MOVIE,TVSHOWS
}