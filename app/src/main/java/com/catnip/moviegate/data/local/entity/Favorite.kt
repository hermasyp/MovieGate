package com.catnip.moviegate.data.local.entity

import androidx.room.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey
    var id: String,
    var type: String,
    var title: String,
    var originalTitle: String,
    var releaseDate: String,
    var posterPath: String
)
enum class ContentType{
    MOVIE,TVSHOWS
}

