package com.example.moviesapp.fragments.list

import com.example.moviesapp.network.RetrofitModule
import com.example.moviesapp.pojo.movies.popular.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieInteractor(private val dispatcher: CoroutineDispatcher) {
    suspend fun getPopularMovies(page: String): List<Result> = withContext(dispatcher) {
        RetrofitModule.apiMovies.getPopularMovies(page = page).results
    }

    suspend fun getNowPlayingMovies(page: String): List<Result> = withContext(dispatcher) {
        RetrofitModule.apiMovies.getNowPlayingMovies(page = page).results
    }

    suspend fun getTopRatedMovies(page: String): List<Result> = withContext(dispatcher) {
        RetrofitModule.apiMovies.getTopRatedMovies(page = page).results
    }

    suspend fun getUpComingMovies(page: String): List<Result> = withContext(dispatcher) {
        RetrofitModule.apiMovies.getUpComingMovies(page = page).results
    }

    suspend fun getSearchMovies(searchValue: String, page: String): List<Result> =
        withContext(dispatcher) {
            RetrofitModule.apiMovies.getSearchMovie(query = searchValue, page = page).results
        }
}