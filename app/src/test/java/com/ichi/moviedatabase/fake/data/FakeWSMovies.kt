package com.ichi.moviedatabase.fake.data

import com.ichi.moviedatabase.network.models.WSMovies

val firstFakeMovie = WSMovies.Movie(
    posterPath = "https://first-movie.com/poster.jpg",
    id = 1,
    title = "First movie",
)

val secondFakeMovie = WSMovies.Movie(
    posterPath = "https://second-movie.com/poster.jpg",
    id = 2,
    title = "Second movie",
)

val nullAttributesFakeMovie = WSMovies.Movie(null, null, null)
