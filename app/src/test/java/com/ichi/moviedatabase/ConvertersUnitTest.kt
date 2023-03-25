package com.ichi.moviedatabase

import com.ichi.moviedatabase.fake.data.firstFakeMovie
import com.ichi.moviedatabase.network.converters.toApp
import com.ichi.moviedatabase.network.models.WSMovies
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ConvertersUnitTest {
    @Test
    fun `convert WSMovies into List of AppHomeMovie, should create valid data`() {
        val wsResults = WSMovies(results = listOf(firstFakeMovie))
        val appHomeMovies = wsResults.results?.map { it.toApp() }

        assertThat(appHomeMovies).isNotNull()
        assertThat(wsResults.results!!.size).isEqualTo(appHomeMovies!!.size)
        assertThat(wsResults.results!!.first().id).isEqualTo(appHomeMovies.first()!!.id)
        assertThat(appHomeMovies.first()!!.posterPath).contains(wsResults.results!!.first().posterPath)
        assertThat(wsResults.results!!.first().title).isEqualTo(appHomeMovies.first()!!.title)
    }
}
