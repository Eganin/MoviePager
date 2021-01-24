package com.example.moviesapp.presentation.movies.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.moviesapp.model.entities.movies.credits.ResponseCredits
import com.example.moviesapp.model.entities.movies.details.ResponseMovieDetail
import com.example.moviesapp.model.network.RetrofitModule
import com.example.moviesapp.model.repositories.MovieRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class MoviesDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    private val _credits = MutableLiveData<ResponseCredits>()
    val credits: LiveData<ResponseCredits> = _credits

    private val _info = MutableLiveData<ResponseMovieDetail>()
    val info: LiveData<ResponseMovieDetail> = _info

    fun startLoadingData() {
        _state.value = true
    }

    fun stopLoadingData() {
        _state.value = false
    }

    fun loadDetailData(id: Long) {
        viewModelScope.launch {
            _info.value = repository.getDetailInfoForMovie(movieId = id.toString())

            _credits.value = repository.getCreditsForMovie(movieId = id.toString())
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