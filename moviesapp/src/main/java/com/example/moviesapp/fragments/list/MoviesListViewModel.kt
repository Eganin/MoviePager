package com.example.moviesapp.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.RetrofitModule
import com.example.moviesapp.pojo.configuration.GenreList
import com.example.moviesapp.pojo.configuration.Images
import com.example.moviesapp.pojo.movies.popular.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesListViewModel(private val interactor: MovieInteractor) :
    ViewModel() {


    private val dispatcher = Dispatchers.IO

    private val _moviesList = MutableLiveData<List<Result>>(emptyList())
    val moviesList: LiveData<List<Result>> = _moviesList

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    var configuration: Images? = null
    var genreList: GenreList? = null

    private var isLoading = true

    fun loadDataModel(type: MovieDataType) {
        viewModelScope.launch {
            coroutineScope {
                _state.value = true

                configuration = getConfiguration()

                genreList = getGenres()

                loadMovies(type=type)

                _state.value = false
            }
        }
    }

    fun loadMovies(type: MovieDataType) {
        if (isLoading) {
            viewModelScope.launch {
                coroutineScope {
                    Counter.count++
                    isLoading = false
                    val movies = when(type){
                        MovieDataType.TOP_RATED -> interactor.getTopRatedMovies(page = Counter.count.toString())
                        MovieDataType.POPULAR -> interactor.getPopularMovies(page = Counter.count.toString())
                        MovieDataType.NOW_PLAYING -> interactor.getNowPlayingMovies(page = Counter.count.toString())
                        MovieDataType.UP_COMING -> interactor.getUpComingMovies(page = Counter.count.toString())
                    }
                    _moviesList.value = movies
                    isLoading = true
                }
            }

        }
    }

    private suspend fun getConfiguration(): Images = withContext(dispatcher) {
        RetrofitModule.apiMovies.getConfiguration().images
    }

    private suspend fun getGenres(): GenreList = withContext(dispatcher) {
        RetrofitModule.apiMovies.getGenres()
    }

}

object Counter {
    var count = 0
}

enum class MovieDataType {
    TOP_RATED, POPULAR, NOW_PLAYING, UP_COMING
}
