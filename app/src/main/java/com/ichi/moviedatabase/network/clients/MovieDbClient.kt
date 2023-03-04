package com.ichi.moviedatabase.network.clients

import com.ichi.moviedatabase.network.models.WSMovie
import com.ichi.moviedatabase.network.models.WSMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbClient {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): WSMovies

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movieId: Int): WSMovie
}
