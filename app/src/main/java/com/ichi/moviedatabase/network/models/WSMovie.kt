package com.ichi.moviedatabase.network.models

import com.google.gson.annotations.SerializedName

// Attributes are all defined as optionals in the documentation
data class WSMovie(
    @SerializedName("backdrop_path") val backdropPath: String?,
    val budget: Int?,
    val genres: List<Genre>?,
    val overview: String?,
    @SerializedName("production_companies") val productionCompanies: List<Company>?,
    @SerializedName("release_date") val releaseDate: String?,
    val revenue: Double?,
    val runtime: Int?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    val status: Status?,
    val title: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
) {
    data class Genre(
        val name: String?,
    )

    data class Company(
        val name: String?,
    )

    data class SpokenLanguage(
        @SerializedName("english_name") val englishName: String?,
    )

    enum class Status {
        @SerializedName("Rumored") RUMORED,
        @SerializedName("Planned") PLANNED,
        @SerializedName("In Production") IN_PRODUCTION,
        @SerializedName("Post Production") POST_PRODUCTION,
        @SerializedName("Released") RELEASED,
        @SerializedName("Canceled") CANCELED,
    }
}
