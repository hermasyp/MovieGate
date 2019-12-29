package com.catnip.moviegate.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.catnip.moviegate.data.local.dao.FavoriteDao
import com.catnip.moviegate.data.local.entity.Favorite

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/



@Database(entities = [Favorite::class],version = 1)
abstract class AppDatabase : RoomDatabase(){
companion object{const val DBNAME = "MoviegateDb"}
    abstract fun favoriteDao():FavoriteDao
}