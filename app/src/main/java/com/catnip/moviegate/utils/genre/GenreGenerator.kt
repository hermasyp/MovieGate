package com.catnip.moviegate.utils.genre

import com.catnip.moviegate.model.common.Genre

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

object GenreGenerator {
    fun getAllGenre(genres : List<Genre>) : String{
        var strGenres = mutableListOf<String>()
        for(genre in genres){
            strGenres.add(genre.name)
        }
        return strGenres.joinToString()
    }
}