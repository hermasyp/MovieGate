package com.catnip.moviegate.data.local.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.catnip.moviegate.data.local.entity.Favorite.Companion.TABLE_NAME

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

@Entity(tableName = TABLE_NAME)
data class Favorite(
    @PrimaryKey
    var id: String,
    var type: String,
    var title: String,
    var originalTitle: String,
    var releaseDate: String,
    var posterPath: String
){
    companion object{
        const val AUTHORITY = "com.catnip.moviegate.provider"
        const val TABLE_NAME = "favorites"
        const val SCHEME = "content"
        const val FAV_ID = "id"
        const val FAV_TYPE = "type"
        const val FAV_TITLE = "title"
        const val FAV_ORIGINAL_TITLE = "originalTitle"
        const val FAV_RELEASE_DATE = "releaseDate"
        const val FAV_POSTER_PATH = "posterPath"
        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }
}
enum class ContentType{
    MOVIE,TVSHOWS
}

