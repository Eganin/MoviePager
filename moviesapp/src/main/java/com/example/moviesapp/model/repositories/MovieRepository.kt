package com.example.moviesapp.model.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.moviesapp.model.entities.movies.credits.Cast
import com.example.moviesapp.model.entities.movies.credits.ResponseCredits
import com.example.moviesapp.model.entities.movies.details.ResponseMovieDetail
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.entities.movies.popular.results.ResultNowPlayong
import com.example.moviesapp.model.entities.movies.popular.results.ResultTopRated
import com.example.moviesapp.model.entities.movies.popular.results.ResultUpComing
import com.example.moviesapp.model.network.RetrofitModule
import com.example.moviesapp.storage.MoviesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(applicationContext: Context) : Repository {
    private val dispatcher = Dispatchers.IO

    private val db = MoviesDatabase.getInstance(context = applicationContext)

    private val moviesDataPopular = db.moviesDaoPopular
    private val moviesDataTopRated = db.moviesDaoTopRated
    private val moviesDataNowPlayong = db.moviesDaoNowPlayong
    private val moviesDataUpComing = db.moviesDaoUpComing
    private val moviesDataDetail = db.moviesDaoDetail
    private val moviesDataCast = db.moviesDaoCast

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

    override suspend fun getConfiguration() = withContext(Dispatchers.IO) {
        RetrofitModule.apiMovies.getConfiguration().images
    }

    override suspend fun getGenres() = withContext(dispatcher) {
        RetrofitModule.apiMovies.getGenres()
    }

    override fun getAllMoviesPopular() = moviesDataPopular.getAllMoviesPopular()


    override suspend fun insertPopularMovies(movies: List<Result>) = withContext(dispatcher) {
        moviesDataPopular.insertPopularMovies(movies = movies)
    }

    override suspend fun deleteAllPopularMovies() = withContext(dispatcher) {
        moviesDataPopular.deleteAllPopularMovies()
    }

    override suspend fun deletePopularMovieById(id: Long) = withContext(dispatcher) {
        moviesDataPopular.deletePopularMovieById(id = id)
    }

    override fun getAllMoviesTopRated() = moviesDataTopRated.getAllMoviesTopRated()

    override suspend fun insertTopRatedMovies(movies: List<ResultTopRated>) {
        moviesDataTopRated.insertTopRatedMovies(movies = movies)
    }

    override suspend fun deleteAllTopRatedMovies() {
        moviesDataTopRated.deleteAllTopRatedMovies()
    }

    override suspend fun deleteTopRatedMovieById(id: Long) {
        moviesDataTopRated.deleteTopRatedMovieById(id = id)
    }

    override fun getAllMoviesNowPlayong() = moviesDataNowPlayong.getAllMoviesNowPlayong()

    override suspend fun insertNowPlayongMovies(movies: List<ResultNowPlayong>) {
        moviesDataNowPlayong.insertNowPlayongMovies(movies = movies)
    }

    override suspend fun deleteAllNowPlayongMovies() {
        moviesDataNowPlayong.deleteAllNowPlayongMovies()
    }

    override suspend fun deleteNowPlayongMovieById(id: Long) {
        moviesDataNowPlayong.deleteNowPlayongMovieById(id = id)
    }

    override fun getAllMoviesUpComing() = moviesDataUpComing.getAllMoviesUpComing()

    override suspend fun insertUpComingMovies(movies: List<ResultUpComing>) {
        moviesDataUpComing.insertUpComingMovies(movies = movies)
    }

    override suspend fun deleteAllUpComingMovies() {
        moviesDataUpComing.deleteAllUpComingMovies()
    }

    override suspend fun deleteUpComingMovieById(id: Long) {
        moviesDataUpComing.deleteUpComingMovieById(id = id)
    }


    override suspend fun getDetailInfoForMovie(movieId: String) =
        RetrofitModule.apiMovies.getDetailInfo(movieId = movieId)


    override suspend fun getCreditsForMovie(movieId: String) =
        RetrofitModule.apiMovies.getCreditsMovie(movieId = movieId)

    override fun getAllDetailMovies(): LiveData<List<ResponseMovieDetail>> =
        moviesDataDetail.getAllDetailMovies()

    override suspend fun insertDetailMovie(movie: ResponseMovieDetail) =
        moviesDataDetail.insertDetailMovie(movie = movie)

    override suspend fun deleteAllDetailMovies() = moviesDataDetail.deleteAllDetailMovies()

    override suspend fun getDetailMovieById(id: Long) = moviesDataDetail.getDetailMovieById(id = id)

    override fun getAllCastMovies()= moviesDataCast.getAllCastMovies()
    override suspend fun insertCastMovie(cast: ResponseCredits) =moviesDataCast.insertCastMovie(cast=cast)

    override suspend fun deleteAllCast() =moviesDataCast.deleteAllCast()

    override suspend fun getCastMovieById(id: Long)= moviesDataCast.getCastMovieById(id=id)

}