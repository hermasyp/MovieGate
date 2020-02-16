package com.catnip.mgfavapp.utils

import android.database.Cursor
import com.catnip.mgfavapp.model.Favorite

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object ContentCursorMapper {

    fun mapCursorToContent(cursor: Cursor?): MutableList<Favorite> {
        val list = mutableListOf<Favorite>()
        cursor?.apply {
            while (moveToNext()){
                val id = getString(getColumnIndexOrThrow(Favorite.FAV_ID))
                val type = getString(getColumnIndexOrThrow(Favorite.FAV_TYPE))
                val title = getString(getColumnIndexOrThrow(Favorite.FAV_TITLE))
                val originalTitle = getString(getColumnIndexOrThrow(Favorite.FAV_ORIGINAL_TITLE))
                val releaseDate = getString(getColumnIndexOrThrow(Favorite.FAV_RELEASE_DATE))
                val posterPath = getString(getColumnIndexOrThrow(Favorite.FAV_POSTER_PATH))
                list.add(Favorite(id,type,title,originalTitle,releaseDate,posterPath))
            }
        }
        return list
    }
}