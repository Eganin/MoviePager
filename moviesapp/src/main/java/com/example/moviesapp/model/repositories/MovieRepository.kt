package com.example.moviesapp.model.repositories

import android.content.Context
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.entities.movies.popular.results.ResultNowPlayong
import com.example.moviesapp.model.entities.movies.popular.results.ResultTopRated
import com.example.moviesapp.model.entities.movies.popular.results.ResultUpComing
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

    override suspend fun getConfiguration() = withContext(Dispatchers.IO){
         RetrofitModule.apiMovies.getConfiguration().images
    }

    override suspend fun getGenres()= withContext(dispatcher) {
        RetrofitModule.apiMovies.getGenres()
    }

    override  fun getAllMoviesPopular() = moviesData.getAllMoviesPopular()


    override suspend fun insertPopularMovies(movies: List<Result>) = withContext(dispatcher) {
        moviesData.insertPopularMovies(movies = movies)
    }

    override suspend fun deleteAllPopularMovies() = withContext(dispatcher) {
        moviesData.deleteAllPopularMovies()
    }

    override suspend fun deletePopularMovieById(id: Long) = withContext(dispatcher) {
        moviesData.deletePopularMovieById(id = id)
    }

    override fun getAllMoviesTopRated()= moviesData.getAllMoviesTopRated()

    override suspend fun insertTopRatedMovies(movies: List<ResultTopRated>) {
        moviesData.insertTopRatedMovies(movies = movies)
    }

    override suspend fun deleteAllTopRatedMovies() {
        moviesData.deleteAllTopRatedMovies()
    }

    override suspend fun deleteTopRatedMovieById(id: Long) {
        moviesData.deleteTopRatedMovieById(id = id)
    }

    override fun getAllMoviesNowPlayong()= moviesData.getAllMoviesNowPlatong()

    override suspend fun insertNowPlayongMovies(movies: List<ResultNowPlayong>) {
        moviesData.insertNowPlayongMovies(movies = movies)
    }

    override suspend fun deleteAllNowPlayongMovies() {
        moviesData.deleteAllNowPlayongMovies()
    }

    override suspend fun deleteNowPlayongMovieById(id: Long) {
        moviesData.deleteNowPlayongMovieById(id = id)
    }

    override fun getAllMoviesUpComing()= moviesData.getAllMoviesUpComing()

    override suspend fun insertUpComingMovies(movies: List<ResultUpComing>) {
        moviesData.insertUpComingMovies(movies = movies)
    }

    override suspend fun deleteAllUpComingMovies() {
        moviesData.deleteAllUpComingMovies()
    }

    override suspend fun deleteUpComingMovieById(id: Long) {
        moviesData.deleteUpComingMovieById(id = id)
    }
}