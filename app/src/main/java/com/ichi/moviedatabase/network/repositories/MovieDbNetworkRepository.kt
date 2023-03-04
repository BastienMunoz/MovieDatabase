package com.ichi.moviedatabase.network.repositories

import com.ichi.moviedatabase.network.clients.MovieDbClient
import com.ichi.moviedatabase.network.models.WSMovie
import com.ichi.moviedatabase.network.models.WSMovies
import javax.inject.Inject

class MovieDbNetworkRepository @Inject constructor(
    private val client: MovieDbClient,
) : MovieDbNetworkRepositoryInterface {
    override suspend fun getPopularMovies(page: Int): WSMovies {
        return client.getPopularMovies(page)
    }

    override suspend fun getMovie(movieId: Int): WSMovie {
        return client.getMovie(movieId)
    }
}
