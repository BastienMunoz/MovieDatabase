package com.ichi.moviedatabase

import com.ichi.moviedatabase.core.Result
import com.ichi.moviedatabase.fake.repositories.FakeMovieDbNetworkRepository
import com.ichi.moviedatabase.usecases.GetPopularMoviesUseCase
import com.google.common.truth.Truth.assertThat
import com.ichi.moviedatabase.fake.data.firstFakeMovie
import com.ichi.moviedatabase.fake.data.nullAttributesFakeMovie
import com.ichi.moviedatabase.fake.data.secondFakeMovie
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPopularMoviesUseCaseTest {
    private lateinit var fakeRepository: FakeMovieDbNetworkRepository
    private lateinit var useCase: GetPopularMoviesUseCase

    @Before
    fun setup() {
        fakeRepository = FakeMovieDbNetworkRepository()
        useCase = GetPopularMoviesUseCase(fakeRepository)
    }

    @Test
    fun `Retrieve two movies, should receive a list of two elements with same ids`() = runBlocking {
        fakeRepository.clearMovies()
        fakeRepository.addMovie(firstFakeMovie)
        fakeRepository.addMovie(secondFakeMovie)

        val params = GetPopularMoviesUseCase.Params(1)
        useCase(params).collect {
            if (it is Result.Success) {
                val movies = it.data
                assertThat(movies).hasSize(2)
                assertThat(movies[0].id).isEqualTo(firstFakeMovie.id)
                assertThat(movies[1].id).isEqualTo(secondFakeMovie.id)
            }
        }
    }

    @Test
    fun `Retrieve one movie with null id, should receive an empty list`() = runBlocking {
        fakeRepository.clearMovies()
        fakeRepository.addMovie(nullAttributesFakeMovie)

        val params = GetPopularMoviesUseCase.Params(1)
        useCase(params).collect {
            if (it is Result.Success) {
                val movies = it.data
                assertThat(movies).hasSize(0)
            }
        }
    }
}
