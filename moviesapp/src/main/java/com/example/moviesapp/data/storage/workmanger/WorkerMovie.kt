package com.example.moviesapp.storage.workmanger

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.moviesapp.application.MovieApp
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.entities.movies.popular.results.ResultNowPlayong
import com.example.moviesapp.model.entities.movies.popular.results.ResultTopRated
import com.example.moviesapp.model.entities.movies.popular.results.ResultUpComing
import com.example.moviesapp.model.repositories.MovieRepository
import com.example.moviesapp.presentation.movies.utils.toResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Collections.max

class WorkerMovie(val context: Context, params: WorkerParameters) : Worker(context, params) {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun doWork(): Result {
        Log.d("AAA", "WPRKEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEER")
        val movieRepository = (context as MovieApp).myComponent.getMovieRepository()
        val notification = movieRepository.movieNotifications
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
                notification.showNotification(
                    movie = getMovieWithHighRating(
                        popularMovies = popularMovies,
                        topRatedMovies = topRatedMovies,
                        nowPlayongMovies = nowPlayongMovies,
                        upComingMovies = upComingMovies
                    ) ?: throw Exception()
                )
            }
            return Result.success()

        } catch (e: Exception) {
            return Result.failure()
        }

    }

    private fun getMovieWithHighRating(
        popularMovies: List<com.example.moviesapp.model.entities.movies.popular.results.Result>,
        topRatedMovies: List<ResultTopRated>,
        nowPlayongMovies: List<ResultNowPlayong>,
        upComingMovies: List<ResultUpComing>
    ): com.example.moviesapp.model.entities.movies.popular.results.Result? {
        val movies = listOf(
            popularMovies.maxByOrNull { it.voteAverage ?: 0.0 },
            topRatedMovies.maxByOrNull { it.voteAverage ?: 0.0 }?.toResult(),
            nowPlayongMovies.maxByOrNull { it.voteAverage ?: 0.0 }?.toResult(),
            upComingMovies.maxByOrNull { it.voteAverage ?: 0.0 }?.toResult()
        )

        return movies.maxByOrNull { it?.voteAverage ?: 0.0 }
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