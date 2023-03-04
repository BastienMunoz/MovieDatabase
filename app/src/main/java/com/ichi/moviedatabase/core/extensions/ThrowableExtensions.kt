package com.ichi.moviedatabase.core.extensions

import com.ichi.moviedatabase.R
import okio.IOException
import retrofit2.HttpException

fun Throwable.getDisplayableMessageResId(): Int {
    return when (this) {
        is IOException -> R.string.error_no_internet_connectivity
        is HttpException -> R.string.error_server_went_wrong
        else -> R.string.error_unexpected
    }
}
