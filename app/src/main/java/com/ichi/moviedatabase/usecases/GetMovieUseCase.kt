package com.ichi.moviedatabase.usecases

import com.ichi.moviedatabase.core.Result
import com.ichi.moviedatabase.network.converters.toApp
import com.ichi.moviedatabase.network.repositories.MovieDbNetworkRepositoryInterface
import com.ichi.moviedatabase.viewmodels.models.AppMovie
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val networkRepository: MovieDbNetworkRepositoryInterface,
) : BaseUseCase<GetMovieUseCase.Params, AppMovie>() {
    data class Params(val movieId: Int) : BaseParameters

    override suspend fun run(params: Params) = flow {
        emit(Result.Loading())

        val wsMovie = networkRepository.getMovie(params.movieId)
        val appMovie = wsMovie.toApp()

        emit(Result.Success(appMovie))
    }
}
