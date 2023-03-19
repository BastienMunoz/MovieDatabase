package com.ichi.moviedatabase.fake.data

import com.ichi.moviedatabase.network.models.WSMovie

val fakeMovie = WSMovie(
    "https://fake-movie.com/backdrop.jpg",
    1_000_000,
    listOf(
        WSMovie.Genre("Action"),
        WSMovie.Genre("Drama"),
    ),
    "A film about something",
    listOf(
        WSMovie.Company("A company"),
    ),
    "2017-04-12",
    10_000_000.0,
    127,
    listOf(
        WSMovie.SpokenLanguage("French"),
        WSMovie.SpokenLanguage("English"),
    ),
    WSMovie.Status.RELEASED,
    "A title",
    4.7,
    1_200,
)
