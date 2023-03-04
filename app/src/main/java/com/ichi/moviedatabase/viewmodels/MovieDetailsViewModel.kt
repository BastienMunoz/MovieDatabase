package com.ichi.moviedatabase.viewmodels

import androidx.lifecycle.viewModelScope
import com.ichi.moviedatabase.core.Result
import com.ichi.moviedatabase.core.extensions.getDisplayableMessageResId
import com.ichi.moviedatabase.usecases.GetMovieUseCase
import com.ichi.moviedatabase.viewmodels.models.AppMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
) : BaseViewModel() {
    private val _movie: MutableStateFlow<AppMovie?> = MutableStateFlow(null)
    val movie: StateFlow<AppMovie?> get() = _movie.asStateFlow()

    fun getMovie(movieId: Int) {
        val params = GetMovieUseCase.Params(movieId)

        viewModelScope.launch {
            getMovieUseCase(params).catch {
                _showLoader.value = false
                _error.value = it.getDisplayableMessageResId()
                _error.value = null // Set to null to consume the previous value only once
            }.collect {
                when (it) {
                    is Result.Loading -> _showLoader.value = true

                    is Result.Success -> {
                        _showLoader.value = false
                        _movie.value = it.data
                    }
                }
            }
        }
    }
}
