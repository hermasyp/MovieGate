package com.catnip.moviegate.data.local.dao

import androidx.room.*
import com.catnip.moviegate.data.local.entity.ContentType
import com.catnip.moviegate.data.local.entity.Favorite

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

//reference : https://android.jlelse.eu/mvp-with-rxjava2-room-koin-6f9492b94500
@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveFavorite(favorite: Favorite)

    @Delete
    fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites where type = :contentType")
    fun getAllFavoriteByType(contentType: ContentType): MutableList<Favorite>

}