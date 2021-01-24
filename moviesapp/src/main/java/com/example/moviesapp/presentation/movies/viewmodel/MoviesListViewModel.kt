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

    private var firstLaunch = true

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

                Log.d("AAA", query.toString())
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
            startLoadingData()
            viewModelScope.launch {
                coroutineScope {
                    Counter.count++
                    isLoading = false
                    when (type) {
                        MovieDataType.TOP_RATED -> {
                            increasePage()
                            val movies =
                                repository.getTopRatedMovies(page = Counter.count.toString())
                            _moviesList.value = movies.topRatedToResult()
                            insertTopRatedMoviesDb(movies=movies)

                        }
                        MovieDataType.POPULAR -> {
                            increasePage()
                            val movies =
                                repository.getPopularMovies(page = Counter.count.toString())
                            _moviesList.value = movies
                            insertPopularMoviesDb(movies=movies)

                        }
                        MovieDataType.NOW_PLAYING -> {
                            increasePage()
                            val movies =
                                repository.getNowPlayingMovies(page = Counter.count.toString())
                            _moviesList.value = movies.nowPlayongToResult()
                            insertNowPlayongMoviesDb(movies=movies)

                        }
                        MovieDataType.UP_COMING -> {
                            increasePage()
                            val movies =
                                repository.getUpComingMovies(page = Counter.count.toString())
                            _moviesList.value = movies.upComingToResult()
                            insertUpComingMoviesDb(movies=movies)

                        }
                        MovieDataType.SEARCH -> query?.let {
                            val movies = repository.getSearchMovies(
                                searchValue = it, page = Counter.count.toString()
                            )
                            _moviesList.value = movies
                        }
                    }

                    isLoading = true
                }
            }
            stopLoadingData()
        }
    }

    fun increasePage() {
        Counter.increase(firstLaunch = firstLaunch)
        firstLaunch = false
    }

    private fun startLoadingData() {
        _state.value = true
    }

    fun stopLoadingData() {
        _state.value = false
    }

    private fun insertPopularMoviesDb(movies: List<Result>) = viewModelScope.launch {
        repository.insertPopularMovies(movies = movies)
    }

    fun deleteAllPopularMoviesDB() = viewModelScope.launch {
        repository.deleteAllPopularMovies()
    }

    private fun insertTopRatedMoviesDb(movies: List<ResultTopRated>) = viewModelScope.launch {
        repository.insertTopRatedMovies(movies = movies)
    }

    fun deleteAllTopRatedMoviesDB() = viewModelScope.launch {
        repository.deleteAllTopRatedMovies()
    }

    private fun insertNowPlayongMoviesDb(movies: List<ResultNowPlayong>) = viewModelScope.launch {
        repository.insertNowPlayongMovies(movies = movies)
    }

    fun deleteAllTNowPlayongMoviesDB() = viewModelScope.launch {
        repository.deleteAllNowPlayongMovies()
    }

    private fun insertUpComingMoviesDb(movies: List<ResultUpComing>) = viewModelScope.launch {
        repository.insertUpComingMovies(movies = movies)
    }

    fun deleteAllTUpComingMoviesDB() = viewModelScope.launch {
        repository.deleteAllUpComingMovies()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: MovieRepository) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MoviesListViewModel(repository = repository) as T
        }
    }

}

object Counter {
    var count = 0
    fun increase(firstLaunch: Boolean) {
        if (count != 1 && firstLaunch) {
            count--
        }
    }
}

enum class MovieDataType {
    TOP_RATED, POPULAR, NOW_PLAYING, UP_COMING, SEARCH
}
