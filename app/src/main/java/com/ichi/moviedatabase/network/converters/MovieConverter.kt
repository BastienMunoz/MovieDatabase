package com.ichi.moviedatabase.network.converters

import com.ichi.moviedatabase.core.Constants
import com.ichi.moviedatabase.network.models.WSMovie
import com.ichi.moviedatabase.viewmodels.models.AppMovie
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

fun WSMovie.toApp(): AppMovie {
    val genres = this.genres?.mapNotNull { it.name } ?: emptyList()
    val productionCompanies = this.productionCompanies?.mapNotNull { it.name } ?: emptyList()
    val spokenLanguages = this.spokenLanguages?.mapNotNull { it.englishName } ?: emptyList()

    val releaseDate = parseDate(this.releaseDate) ?: ""

    val status = when (status) {
        WSMovie.Status.RUMORED -> AppMovie.Status.RUMORED
        WSMovie.Status.PLANNED -> AppMovie.Status.PLANNED
        WSMovie.Status.IN_PRODUCTION -> AppMovie.Status.IN_PRODUCTION
        WSMovie.Status.POST_PRODUCTION -> AppMovie.Status.POST_PRODUCTION
        WSMovie.Status.RELEASED -> AppMovie.Status.RELEASED
        WSMovie.Status.CANCELED -> AppMovie.Status.CANCELED
        null -> AppMovie.Status.UNKNOWN
    }

    return AppMovie(
        getImageUrlFromPath(backdropPath),
        title ?: "",
        releaseDate,
        convertRuntimeIntoHoursMinutesFormat(runtime),
        overview ?: "",
        genres,
        productionCompanies,
        formatNumber(budget?.toDouble() ?: 0.0, Constants.NUMBER_FORMAT_CURRENCY),
        formatNumber(revenue ?: 0.0, Constants.NUMBER_FORMAT_CURRENCY),
        status,
        spokenLanguages,
        formatNumber(voteAverage ?: 0.0, Constants.NUMBER_FORMAT_RATING),
        voteCount ?: 0,
    )
}

private fun parseDate(stringDate: String?): String? {
    if (stringDate == null) {
        return null
    }

    /*
     * As the app is only available in English the Locale is forced to Locale.ENGLISH
     * To take user's locale into account we have to use: Locale.getDefault() instead
     */
    val movieDbFormat = SimpleDateFormat(Constants.MOVIE_DB_DATE_FORMAT, Locale.ENGLISH)

    return try {
        val date = movieDbFormat.parse(stringDate)

        if (date == null) {
            null
        } else {
            DateFormat.getDateInstance().format(date)
        }
    } catch (e: Exception) {
        null
    }
}

private fun convertRuntimeIntoHoursMinutesFormat(runtime: Int?): AppMovie.Runtime {
    return when (runtime) {
        null -> AppMovie.Runtime()

        in 0 until 60 -> AppMovie.Runtime(minutes = "$runtime")

        else -> {
            val hours = runtime / 60
            val minutes = runtime % 60

            if (minutes == 0) {
                AppMovie.Runtime(hours = "$hours")
            } else if (minutes < 10) {
                AppMovie.Runtime(hours = "$hours", minutes = "0$minutes")
                AppMovie.Runtime()
            } else {
                AppMovie.Runtime(hours = "$hours", minutes = "$minutes")
            }
        }
    }
}

private fun getImageUrlFromPath(path: String?): String {
    return if (path != null) {
        Constants.MOVIE_DB_IMAGES_BASE_URL + path
    } else {
        ""
    }
}

private fun formatNumber(number: Double, format: String): String {
    if (number == 0.0) {
        return ""
    }

    val formatSymbols = DecimalFormatSymbols(Locale.ENGLISH)
    formatSymbols.decimalSeparator = '.'
    formatSymbols.groupingSeparator = ' '

    val formatter = DecimalFormat(format, formatSymbols)
    return formatter.format(number)
}
