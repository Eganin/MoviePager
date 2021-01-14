package com.example.moviesapp.presentation.movies.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.moviesapp.model.entities.configuration.GenreList
import com.example.moviesapp.model.entities.configuration.Images
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import com.example.moviesapp.model.entities.movies.popular.Result
import com.example.moviesapp.model.repositories.MovieRepository

class MoviesListViewModel(private val repository: MovieRepository) :
    ViewModel() {

    private var firstLaunch = true

    private val _moviesList = MutableLiveData<List<Result>>(emptyList())
    val moviesList: LiveData<List<Result>> = _moviesList

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

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
                    val movies = when (type) {
                        MovieDataType.TOP_RATED -> {
                            increasePage()
                            repository.getTopRatedMovies(page = Counter.count.toString())
                        }
                        MovieDataType.POPULAR -> {
                            increasePage()
                            repository.getPopularMovies(page = Counter.count.toString())
                        }
                        MovieDataType.NOW_PLAYING -> {
                            increasePage()
                            repository.getNowPlayingMovies(page = Counter.count.toString())
                        }
                        MovieDataType.UP_COMING -> {
                            increasePage()
                            repository.getUpComingMovies(page = Counter.count.toString())
                        }
                        MovieDataType.SEARCH -> query?.let {
                            repository.getSearchMovies(
                                searchValue = it, page = Counter.count.toString()
                            )
                        }
                    }
                    movies?.let { _moviesList.value = it }

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

    fun startLoadingData() {
        _state.value = true
    }

    fun stopLoadingData() {
        _state.value = false
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
