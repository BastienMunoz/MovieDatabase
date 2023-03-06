package com.ichi.moviedatabase

import com.ichi.moviedatabase.network.converters.toApp
import com.ichi.moviedatabase.network.models.WSMovies
import org.junit.Test

class ConvertersUnitTest {
    val dummyPosterUrl = "https://dummy-url.com/poster.jpg"
    val dummyId = (0..1_000_000).random()
    val dummyTitle = "Terminator"

    @Test
    fun test_convertWSMovies_success() {
        val wsMovie = WSMovies.Movie(
            posterPath = dummyPosterUrl,
            id = dummyId,
            title = dummyTitle,
        )
        val wsResults = WSMovies(results = listOf(wsMovie))
        val appHomeMovies = wsResults.results?.map { it.toApp() }

        assert(appHomeMovies != null)
        assert(wsResults.results!!.size == appHomeMovies!!.size)
        assert(wsResults.results!!.first().id == appHomeMovies.first()!!.id)
        assert(wsResults.results!!.first().posterPath == appHomeMovies.first()!!.posterPath)
        assert(wsResults.results!!.first().title == appHomeMovies.first()!!.title)
    }
}
