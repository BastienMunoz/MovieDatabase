package com.ichi.moviedatabase.viewmodels

import androidx.lifecycle.viewModelScope
import com.ichi.moviedatabase.core.Result
import com.ichi.moviedatabase.core.extensions.getDisplayableMessageResId
import com.ichi.moviedatabase.usecases.GetPopularMoviesUseCase
import com.ichi.moviedatabase.viewmodels.models.AppHomeMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
) : BaseViewModel() {
    private val _popularMovies: MutableStateFlow<List<AppHomeMovie>> = MutableStateFlow(listOf())
    val popularMovies: StateFlow<List<AppHomeMovie>> get() = _popularMovies.asStateFlow()

    private var page = 1

    fun getPopularMovies() {
        val params = GetPopularMoviesUseCase.Params(page)

        viewModelScope.launch {
            getPopularMoviesUseCase(params).catch {
                _showLoader.value = false
                _error.value = it.getDisplayableMessageResId()
                _error.value = null // Set to null to consume the previous value only once
            }.collect {
                when (it) {
                    is Result.Loading -> _showLoader.value = true

                    is Result.Success -> {
                        _showLoader.value = false
                        _popularMovies.value += it.data
                        page++
                    }
                }
            }
        }
    }

    fun resetMovies() {
        page = 1
        _popularMovies.value = emptyList()
        getPopularMovies()
    }
}
