package com.example.moviesapp.ui.presentation.movies.viewmodel

import androidx.lifecycle.*
import com.example.moviesapp.model.entities.movies.credits.ResponseCredits
import com.example.moviesapp.model.entities.movies.details.ResponseMovieDetail
import com.example.moviesapp.model.repositories.MovieRepository
import kotlinx.coroutines.*

class MoviesDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> _error.value = true }

    private val viewScope = CoroutineScope(SupervisorJob() + Dispatchers.Main + exceptionHandler)

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    private val _credits = MutableLiveData<ResponseCredits>()
    val credits: LiveData<ResponseCredits> = _credits

    private val _info = MutableLiveData<ResponseMovieDetail>()
    val info: LiveData<ResponseMovieDetail> = _info

    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean> = _error

    fun startLoadingData() {
        _state.value = true
    }

    fun stopLoadingData() {
        _state.value = false
    }

    fun loadDetailData(id: Long) {
        viewScope.launch {
            _info.value = repository.getDetailInfoForMovie(movieId = id.toString())

            _credits.value = repository.getCreditsForMovie(movieId = id.toString())
        }
    }

    fun loadDetailDataFromDB(id: Long) {
        viewScope.launch {
            _info.value = repository.getDetailMovieById(id = id)

            _credits.value = repository.getCastMovieById(id = id)
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: MovieRepository) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MoviesDetailsViewModel(repository = repository) as T
        }
    }
}