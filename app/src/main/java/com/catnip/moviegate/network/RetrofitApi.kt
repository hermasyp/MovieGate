package com.catnip.moviegate.network

import com.catnip.moviegate.BuildConfig
import com.catnip.moviegate.model.movies.Movies
import com.catnip.moviegate.model.tvshows.TvShows
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

interface RetrofitApi {
    //TODO : api interface will written in here.

    @GET("/discover/movie")
    fun getMovies(@Query("page") page: Int):Single<Movies>

    @GET("/discover/tv")
    fun getTvShows(@Query("page") page: Int):Single<TvShows>

    companion object{
        operator fun invoke() : RetrofitApi{
            val authInterceptor = Interceptor {chain->
                val newUrl = chain.request().url()
                    .newBuilder()
                    .addQueryParameter("api_key",BuildConfig.API_KEY)
                    .addQueryParameter("language","en-US")
                    .build()
                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_API_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RetrofitApi::class.java)
        }
    }
}