package com.example.moviesapp.storage.workmanger

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.moviesapp.application.MovieApp
import com.example.moviesapp.model.entities.movies.popular.results.ResultNowPlayong
import com.example.moviesapp.model.entities.movies.popular.results.ResultTopRated
import com.example.moviesapp.model.entities.movies.popular.results.ResultUpComing
import com.example.moviesapp.model.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkerMovie(val context: Context, params: WorkerParameters) : Worker(context, params) {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun doWork(): Result {
        Log.d("DATADATA","DATADATADATADATADATADATADATADATADATADATADATA")
        val movieRepository = (context as MovieApp).myComponent.getMovieRepository()
        try {
            scope.launch {
                val popularMovies = movieRepository.getPopularMovies(page = "1")
                val topRatedMovies = movieRepository.getTopRatedMovies(page = "1")
                val nowPlayongMovies = movieRepository.getNowPlayingMovies(page = "1")
                val upComingMovies = movieRepository.getUpComingMovies(page = "1")

                deleteMoviesFromDb(movieRepository = movieRepository)
                insertMovies(
                    movieRepository = movieRepository,
                    popularMovies = popularMovies,
                    topRatedMovies = topRatedMovies,
                    nowPlayongMovies = nowPlayongMovies,
                    upComingMovies = upComingMovies
                )
                Log.d("DATADATA","DATADATADATADATADATADATADATADATADATADATADATA")
            }
            return Result.success()

        } catch (e: Exception) {
            return Result.failure()
        }

    }

    private suspend fun deleteMoviesFromDb(movieRepository: MovieRepository) {
        movieRepository.deleteAllPopularMovies()
        movieRepository.deleteAllTopRatedMovies()
        movieRepository.deleteAllNowPlayongMovies()
        movieRepository.deleteAllUpComingMovies()
    }

    private suspend fun insertMovies(
        movieRepository: MovieRepository,
        popularMovies: List<com.example.moviesapp.model.entities.movies.popular.results.Result>,
        topRatedMovies: List<ResultTopRated>,
        nowPlayongMovies: List<ResultNowPlayong>,
        upComingMovies: List<ResultUpComing>
    ) {
        movieRepository.insertPopularMovies(movies = popularMovies)
        movieRepository.insertTopRatedMovies(movies = topRatedMovies)
        movieRepository.insertNowPlayongMovies(movies = nowPlayongMovies)
        movieRepository.insertUpComingMovies(movies = upComingMovies)
    }
}