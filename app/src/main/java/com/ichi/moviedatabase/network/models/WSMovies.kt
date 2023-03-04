package com.ichi.moviedatabase.network.models

import com.google.gson.annotations.SerializedName

// Attributes are all defined as optionals in the documentation
data class WSMovies(
    val results: List<Movie>?,
) {
    data class Movie(
        @SerializedName("poster_path") val posterPath: String?,
        val id: Int?,
        val title: String?,
    )
}
