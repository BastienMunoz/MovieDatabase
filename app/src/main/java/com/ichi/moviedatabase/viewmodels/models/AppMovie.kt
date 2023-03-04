package com.ichi.moviedatabase.viewmodels.models

data class AppMovie(
    val backdropPath: String,
    val title: String,
    val releaseDate: String,
    val runtime: Runtime,
    val overview: String,
    val genres: List<String>,
    val productionCompanies: List<String>,
    val budget: String,
    val revenue: String,
    val status: Status,
    val spokenLanguages: List<String>,
    val averageRating: String,
    val ratings: Int,
) {
    enum class Status {
        UNKNOWN,
        RUMORED,
        PLANNED,
        IN_PRODUCTION,
        POST_PRODUCTION,
        RELEASED,
        CANCELED,
    }

    data class Runtime(
        val hours: String = "",
        val minutes: String = "",
    ) {
        fun isEmpty(): Boolean = (hours.isEmpty() && minutes.isEmpty())
    }
}
