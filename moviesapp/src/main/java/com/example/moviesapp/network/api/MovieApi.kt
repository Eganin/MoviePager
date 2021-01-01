package com.example.moviesapp.network.api

import com.example.moviesapp.network.RetrofitModule.API_KEY
import com.example.moviesapp.network.RetrofitModule.DEFAULT_LANGUAGE
import com.example.moviesapp.network.RetrofitModule.PARAM_API_KEY
import com.example.moviesapp.network.RetrofitModule.PARAM_LANGUAGE
import com.example.moviesapp.network.RetrofitModule.PARAM_PAGE
import com.example.moviesapp.pojo.configuration.GenreList
import com.example.moviesapp.pojo.configuration.ResponseConfiguration
import com.example.moviesapp.pojo.movies.details.ResponseMovieDetail
import com.example.moviesapp.pojo.movies.popular.ResponseMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/configuration")
    suspend fun getConfiguration(@Query(PARAM_API_KEY) apiKey: String = API_KEY): ResponseConfiguration

    @GET("/movie/popular")
    suspend fun getPopularMovies(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(PARAM_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Query(PARAM_PAGE) page: String
    ): ResponseMovies

    @GET("/movie/{movie_id}")
    suspend fun getDetailInfo(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(PARAM_LANGUAGE) language: String = DEFAULT_LANGUAGE,
        @Path("movie_id") movieId: String
    ): List<ResponseMovieDetail>

    @GET("/genre/movie/list")
    suspend fun getGenres(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(PARAM_LANGUAGE) language: String = DEFAULT_LANGUAGE
    ): GenreList
}