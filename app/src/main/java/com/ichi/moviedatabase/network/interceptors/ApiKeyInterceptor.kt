package com.ichi.moviedatabase.network.interceptors

import com.ichi.moviedatabase.core.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val newUrl = chain.request().url.newBuilder().addQueryParameter(Constants.MOVIE_DB_AP_KEY_QUERY, Constants.MOVIE_DB_API_KEY).build()

        request.url(newUrl)
        return chain.proceed(request.build())
    }
}
