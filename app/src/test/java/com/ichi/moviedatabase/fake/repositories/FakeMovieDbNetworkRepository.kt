package com.ichi.moviedatabase.fake.repositories

import com.ichi.moviedatabase.fake.data.fakeWSMovie
import com.ichi.moviedatabase.network.models.WSMovie
import com.ichi.moviedatabase.network.models.WSMovies
import com.ichi.moviedatabase.network.repositories.MovieDbNetworkRepositoryInterface

class FakeMovieDbNetworkRepository : MovieDbNetworkRepositoryInterface {
    private val movies = mutableListOf<WSMovies.Movie>()

    fun addMovie(movie: WSMovies.Movie) {
        movies.add(movie)
    }

    fun clearMovies() {
        movies.clear()
    }

    override suspend fun getPopularMovies(page: Int): WSMovies {
         return WSMovies(movies)
    }

    override suspend fun getMovie(movieId: Int): WSMovie {
        return fakeWSMovie
    }
}