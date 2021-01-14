package com.example.moviesapp.model.network

import com.example.moviesapp.model.network.api.MovieApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object RetrofitModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    const val API_KEY = "0d2c6c8ec17d73125f9c754d030f02eb"
    const val PARAM_API_KEY = "api_key"
    const val PARAM_LANGUAGE = "language"
    const val DEFAULT_LANGUAGE = "en-US"
    const val PARAM_PAGE = "page"
    const val PARAM_QUERY = "query"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()

    val apiMovies: MovieApi = retrofit.create()

}