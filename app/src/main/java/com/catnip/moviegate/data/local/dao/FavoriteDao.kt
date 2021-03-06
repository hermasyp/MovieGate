package com.catnip.moviegate.data.local.dao

import androidx.room.*
import com.catnip.moviegate.data.local.entity.ContentType
import com.catnip.moviegate.data.local.entity.Favorite
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableAmb

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

//reference : https://android.jlelse.eu/mvp-with-rxjava2-room-koin-6f9492b94500
//https://github.com/ashwini009/TvFlix.git
@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite) : Completable

    @Delete
    fun delete(favorite: Favorite) : Completable

    @Query("SELECT * FROM favorites where type = :contentType")
    fun getAll(contentType: String): Single<MutableList<Favorite>>

    @Query("SELECT * FROM favorites where id = :id")
    fun isContentFavorited(id: String?): Single<MutableList<Favorite>>

}