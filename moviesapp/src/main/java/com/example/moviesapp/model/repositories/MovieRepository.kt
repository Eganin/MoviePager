package com.example.moviesapp.model.repositories

import android.content.Context
import com.example.moviesapp.model.entities.movies.popular.Result
import com.example.moviesapp.model.network.RetrofitModule
import com.example.moviesapp.storage.MoviesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(applicationContext: Context) :Repository {
    private val dispatcher = Dispatchers.IO

    private val db = MoviesDatabase.getInstance(context = applicationContext)

    private val moviesData = db.moviesDao

    override suspend fun getPopularMovies(page: String) = withContext(dispatcher) {
        RetrofitModule.apiMovies.getPopularMovies(page = page).results
    }

    override suspend fun getNowPlayingMovies(page: String) = withContext(dispatcher) {
        RetrofitModule.apiMovies.getNowPlayingMovies(page = page).results
    }

    override suspend fun getTopRatedMovies(page: String) = withContext(dispatcher) {
        RetrofitModule.apiMovies.getTopRatedMovies(page = page).results
    }

    override suspend fun getUpComingMovies(page: String) = withContext(dispatcher) {
        RetrofitModule.apiMovies.getUpComingMovies(page = page).results
    }

    override suspend fun getSearchMovies(searchValue: String, page: String) =
        withContext(dispatcher) {
            RetrofitModule.apiMovies.getSearchMovie(query = searchValue, page = page).results
        }

    override suspend fun getConfiguration() = withContext(dispatcher) {
        RetrofitModule.apiMovies.getConfiguration().images
    }

    override suspend fun getGenres()= withContext(dispatcher) {
        RetrofitModule.apiMovies.getGenres()
    }

    override suspend fun getAllMovies() = withContext(dispatcher) {
        moviesData.getAllMovies()
    }

    override suspend fun insertMovies(movies: List<Result>) = withContext(dispatcher) {
        moviesData.insertMovies(movies = movies)
    }

    override suspend fun deleteAllMovies() = withContext(dispatcher) {
        moviesData.deleteAllMovies()
    }

    override suspend fun deleteMovieById(id: Long) = withContext(dispatcher) {
        moviesData.deleteMovieById(id = id)
    }
}