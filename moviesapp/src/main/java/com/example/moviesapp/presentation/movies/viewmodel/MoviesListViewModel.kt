package com.example.moviesapp.presentation.movies.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.moviesapp.model.entities.configuration.GenreList
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.entities.movies.popular.results.ResultNowPlayong
import com.example.moviesapp.model.entities.movies.popular.results.ResultTopRated
import com.example.moviesapp.model.entities.movies.popular.results.ResultUpComing
import com.example.moviesapp.model.repositories.MovieRepository
import com.example.moviesapp.presentation.movies.utils.*

import kotlinx.coroutines.*

class MoviesListViewModel(private val repository: MovieRepository) :
    ViewModel() {
    private val _moviesList = MutableLiveData<List<Result>>()
    val moviesList: LiveData<List<Result>> = _moviesList


    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    val popularMovies = repository.getAllMoviesPopular()
    val topRatedMovies = repository.getAllMoviesTopRated()
    val nowPlayongMovies = repository.getAllMoviesNowPlayong()
    val upComingMovies = repository.getAllMoviesUpComing()

    var configuration: Images? = null
    var genreList: GenreList? = null

    private var isLoading = true


    fun loadDataModel(type: MovieDataType, query: String? = null) {
        viewModelScope.launch {
            coroutineScope {
                startLoadingData()

                configuration = repository.getConfiguration()

                genreList = repository.getGenres()

                if (query == null || query == "null" || query == "") {
                    loadMovies(type = type)
                } else {
                    loadMovies(type = type, query = query)
                }

                stopLoadingData()
            }
        }
    }

    fun loadMovies(type: MovieDataType, query: String? = null) {

        if (isLoading) {
            viewModelScope.launch {
                coroutineScope {
                    isLoading = false
                    when (type) {
                        MovieDataType.TOP_RATED -> {
                            CounterTopRated.count++
                            val movies =
                                repository.getTopRatedMovies(page = CounterTopRated.count.toString())
                            _moviesList.value = movies.topRatedToResult()
                            movies.map { saveDB(id=it.id) }
                            insertTopRatedMoviesDb(movies = movies)


                        }
                        MovieDataType.POPULAR -> {
                            CounterPopular.count++
                            val movies =
                                repository.getPopularMovies(page = CounterPopular.count.toString())
                            _moviesList.value = movies
                            movies.map { saveDB(id=it.id) }
                            insertPopularMoviesDb(movies = movies)

                        }
                        MovieDataType.NOW_PLAYING -> {
                            CounterNowPlayong.count++
                            val movies =
                                repository.getNowPlayingMovies(page = CounterNowPlayong.count.toString())
                            _moviesList.value = movies.nowPlayongToResult()
                            movies.map { saveDB(id=it.id) }
                            insertNowPlayongMoviesDb(movies = movies)

                        }
                        MovieDataType.UP_COMING -> {
                            CounterUpcoming.count++
                            val movies =
                                repository.getUpComingMovies(page = CounterUpcoming.count.toString())
                            _moviesList.value = movies.upComingToResult()
                            movies.map { saveDB(id=it.id) }
                            insertUpComingMoviesDb(movies = movies)

                        }
                        MovieDataType.SEARCH -> query?.let {
                            CounterSearch.count++
                            val movies = repository.getSearchMovies(
                                searchValue = it, page = CounterSearch.count.toString()
                            )
                            _moviesList.value = movies
                        }
                    }

                    isLoading = true
                }
            }
        }
    }


    private fun startLoadingData() {
        _state.value = true
    }

    fun stopLoadingData() {
        _state.value = false
    }

    private fun insertPopularMoviesDb(movies: List<Result>) = viewModelScope.launch {
        startLoadingData()
        repository.insertPopularMovies(movies = movies)
        stopLoadingData()
    }

    fun deleteAllPopularMoviesDB() = viewModelScope.launch {
        startLoadingData()
        repository.deleteAllPopularMovies()
        stopLoadingData()
    }

    private fun insertTopRatedMoviesDb(movies: List<ResultTopRated>) = viewModelScope.launch {
        startLoadingData()
        repository.insertTopRatedMovies(movies = movies)
        stopLoadingData()
    }

    fun deleteAllTopRatedMoviesDB() = viewModelScope.launch {
        startLoadingData()
        repository.deleteAllTopRatedMovies()
        stopLoadingData()
    }

    private fun insertNowPlayongMoviesDb(movies: List<ResultNowPlayong>) = viewModelScope.launch {
        startLoadingData()
        repository.insertNowPlayongMovies(movies = movies)
        stopLoadingData()
    }

    fun deleteAllTNowPlayongMoviesDB() = viewModelScope.launch {
        startLoadingData()
        repository.deleteAllNowPlayongMovies()
        stopLoadingData()
    }

    private fun insertUpComingMoviesDb(movies: List<ResultUpComing>) = viewModelScope.launch {
        startLoadingData()
        repository.insertUpComingMovies(movies = movies)
        stopLoadingData()
    }

    fun deleteAllTUpComingMoviesDB() = viewModelScope.launch {
        startLoadingData()
        repository.deleteAllUpComingMovies()
        stopLoadingData()
    }

    private fun saveDetailInfo(movieId: Long) = viewModelScope.launch {
        val detailMovie = repository.getDetailInfoForMovie(movieId = movieId.toString())
        repository.insertDetailMovie(movie = detailMovie)
    }
    private suspend fun saveDB(id : Long){
        startLoadingData()
        saveDetailInfo(movieId = id)
        val cast = repository.getCreditsForMovie(movieId = id.toString())
        repository.insertCastMovie(cast = cast)
        stopLoadingData()
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: MovieRepository) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MoviesListViewModel(repository = repository) as T
        }
    }

}

enum class MovieDataType {
    TOP_RATED, POPULAR, NOW_PLAYING, UP_COMING, SEARCH
}
