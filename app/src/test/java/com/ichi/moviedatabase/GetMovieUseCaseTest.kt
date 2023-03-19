package com.ichi.moviedatabase

import com.ichi.moviedatabase.core.Result
import com.ichi.moviedatabase.fake.repositories.FakeMovieDbNetworkRepository
import com.ichi.moviedatabase.usecases.GetMovieUseCase
import com.google.common.truth.Truth.assertThat
import com.ichi.moviedatabase.viewmodels.models.AppMovie
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMovieUseCaseTest {
    private lateinit var fakeRepository: FakeMovieDbNetworkRepository
    private lateinit var useCase: GetMovieUseCase

    @Before
    fun setup() {
        fakeRepository = FakeMovieDbNetworkRepository()
        useCase = GetMovieUseCase(fakeRepository)
    }

    @Test
    fun `Retrieve movie with id, should receive an AppMovie`() = runBlocking {
        val params = GetMovieUseCase.Params(123)
        useCase(params).collect {
            if (it is Result.Success) {
                assertThat(it.data).isInstanceOf(AppMovie::class.java)
            }
        }
    }
}
