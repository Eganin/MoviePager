package com.example.moviesapp.model.repositories

import com.example.moviesapp.model.entities.configuration.GenreList
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.network.RetrofitModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {
    private val dispatcher = Dispatchers.IO

    suspend fun getPopularMovies(page: String) = withContext(dispatcher) {
        RetrofitModule.apiMovies.getPopularMovies(page = page).results
    }

    suspend fun getNowPlayingMovies(page: String) = withContext(dispatcher) {
        RetrofitModule.apiMovies.getNowPlayingMovies(page = page).results
    }

    suspend fun getTopRatedMovies(page: String)= withContext(dispatcher) {
        RetrofitModule.apiMovies.getTopRatedMovies(page = page).results
    }

    suspend fun getUpComingMovies(page: String) = withContext(dispatcher) {
        RetrofitModule.apiMovies.getUpComingMovies(page = page).results
    }

    suspend fun getSearchMovies(searchValue: String, page: String) =
        withContext(dispatcher) {
            RetrofitModule.apiMovies.getSearchMovie(query = searchValue, page = page).results
        }

    suspend fun getConfiguration(): Images = withContext(dispatcher) {
        RetrofitModule.apiMovies.getConfiguration().images
    }

    suspend fun getGenres(): GenreList = withContext(dispatcher) {
        RetrofitModule.apiMovies.getGenres()
    }
}