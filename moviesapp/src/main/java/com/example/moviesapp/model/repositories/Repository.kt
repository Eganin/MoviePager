package com.example.moviesapp.model.repositories

import androidx.lifecycle.LiveData
import com.example.moviesapp.model.entities.configuration.GenreList
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.entities.movies.popular.results.ResultNowPlayong
import com.example.moviesapp.model.entities.movies.popular.results.ResultTopRated
import com.example.moviesapp.model.entities.movies.popular.results.ResultUpComing

interface Repository {
    suspend fun getPopularMovies(page: String) : List<Result>

    suspend fun getNowPlayingMovies(page: String) : List<ResultNowPlayong>

    suspend fun getTopRatedMovies(page: String) : List<ResultTopRated>

    suspend fun getUpComingMovies(page: String) : List<ResultUpComing>

    suspend fun getSearchMovies(searchValue: String, page: String) : List<Result>

    suspend fun getConfiguration(): Images

    suspend fun getGenres(): GenreList

    fun getAllMoviesPopular() : LiveData<List<Result>>

    suspend fun insertPopularMovies(movies: List<Result>)

    suspend fun deleteAllPopularMovies()

    suspend fun deletePopularMovieById(id: Long)


    fun getAllMoviesTopRated() : LiveData<List<ResultTopRated>>

    suspend fun insertTopRatedMovies(movies: List<ResultTopRated>)

    suspend fun deleteAllTopRatedMovies()

    suspend fun deleteTopRatedMovieById(id: Long)


    fun getAllMoviesNowPlayong() : LiveData<List<ResultNowPlayong>>

    suspend fun insertNowPlayongMovies(movies: List<ResultNowPlayong>)

    suspend fun deleteAllNowPlayongMovies()

    suspend fun deleteNowPlayongMovieById(id: Long)

    fun getAllMoviesUpComing() : LiveData<List<ResultUpComing>>

    suspend fun insertUpComingMovies(movies: List<ResultUpComing>)

    suspend fun deleteAllUpComingMovies()

    suspend fun deleteUpComingMovieById(id: Long)
}