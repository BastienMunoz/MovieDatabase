package com.ichi.moviedatabase.usecases

import com.ichi.moviedatabase.core.Result
import com.ichi.moviedatabase.network.converters.toApp
import com.ichi.moviedatabase.network.repositories.MovieDbNetworkRepositoryInterface
import com.ichi.moviedatabase.viewmodels.models.AppHomeMovie
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val networkRepository: MovieDbNetworkRepositoryInterface,
) : BaseUseCase<GetPopularMoviesUseCase.Params, List<AppHomeMovie>>() {
    data class Params(val page: Int) : BaseParameters

    override suspend fun run(params: Params) = flow {
        emit(Result.Loading())

        val wsPopularMovies = networkRepository.getPopularMovies(params.page)
        val appPopularMovies = wsPopularMovies.results?.mapNotNull { it.toApp() } ?: emptyList()

        emit(Result.Success(appPopularMovies))
    }
}
