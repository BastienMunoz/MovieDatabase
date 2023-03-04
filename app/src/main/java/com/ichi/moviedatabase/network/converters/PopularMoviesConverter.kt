package com.ichi.moviedatabase.network.converters

import com.ichi.moviedatabase.core.Constants
import com.ichi.moviedatabase.network.models.WSMovies
import com.ichi.moviedatabase.viewmodels.models.AppHomeMovie

fun WSMovies.Movie.toApp(): AppHomeMovie? {
    /*
     * According to the documentation all fields are nullables
     * If id is null we can't deal with this movie, so it's better to not display it to the user if they can't interact with it
     */
    if (id == null) {
        return null
    }

    val posterPath = if (posterPath != null) {
        Constants.MOVIE_DB_IMAGES_BASE_URL + posterPath
    } else {
        ""
    }

    return AppHomeMovie(
        id,
        title ?: "",
        posterPath,
    )
}
