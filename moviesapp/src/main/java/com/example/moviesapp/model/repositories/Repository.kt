package com.example.moviesapp.model.repositories

import androidx.lifecycle.LiveData
import com.example.moviesapp.model.entities.configuration.GenreList
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.popular.Result

interface Repository {
    suspend fun getPopularMovies(page: String) : List<Result>

    suspend fun getNowPlayingMovies(page: String) : List<Result>

    suspend fun getTopRatedMovies(page: String) : List<Result>

    suspend fun getUpComingMovies(page: String) : List<Result>

    suspend fun getSearchMovies(searchValue: String, page: String) : List<Result>

    suspend fun getConfiguration(): Images

    suspend fun getGenres(): GenreList

    fun getAllMovies() : LiveData<List<Result>>

    suspend fun insertMovies(movies: List<Result>)

    suspend fun deleteAllMovies()

    suspend fun deleteMovieById(id: Long)
}