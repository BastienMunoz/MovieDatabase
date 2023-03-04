package com.ichi.moviedatabase.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ichi.moviedatabase.BuildConfig
import com.ichi.moviedatabase.core.Constants
import com.ichi.moviedatabase.core.imageloader.ImageLoader
import com.ichi.moviedatabase.core.imageloader.ImageLoaderInterface
import com.ichi.moviedatabase.network.clients.MovieDbClient
import com.ichi.moviedatabase.network.interceptors.ApiKeyInterceptor
import com.ichi.moviedatabase.network.repositories.MovieDbNetworkRepository
import com.ichi.moviedatabase.network.repositories.MovieDbNetworkRepositoryInterface
import com.ichi.moviedatabase.usecases.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object HiltDependencies {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideHttpClient(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            httpClient.addInterceptor(logging)
        }

        httpClient.addInterceptor(ApiKeyInterceptor())

        return httpClient
    }

    @Provides
    fun provideMovieDbClient(gson: Gson, httpClient: OkHttpClient.Builder): MovieDbClient {
        return Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(Constants.MOVIE_DB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieDbClient::class.java)
    }

    @Provides
    fun provideGetPopularMoviesUseCase(networkRepository: MovieDbNetworkRepositoryInterface) = GetPopularMoviesUseCase(networkRepository)

    @Provides
    fun provideMovieDbNetworkRepository(client: MovieDbClient): MovieDbNetworkRepositoryInterface = MovieDbNetworkRepository(client)

    @Provides
    fun provideImageLoader(): ImageLoaderInterface = ImageLoader()
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface UnsupportedByHiltComponentDependencies {
    fun getImageLoader(): ImageLoaderInterface
}
