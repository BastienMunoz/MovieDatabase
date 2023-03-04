package com.ichi.moviedatabase.network.repositories

import com.ichi.moviedatabase.network.models.WSMovie
import com.ichi.moviedatabase.network.models.WSMovies

interface MovieDbNetworkRepositoryInterface {
    suspend fun getPopularMovies(page: Int): WSMovies
    suspend fun getMovie(movieId: Int): WSMovie
}
