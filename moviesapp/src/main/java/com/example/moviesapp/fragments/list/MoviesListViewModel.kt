package com.example.moviesapp.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.pojo.movies.popular.Result
import kotlinx.coroutines.launch

class MoviesListViewModel(private val interactor: MovieInteractor) :
    ViewModel() {

    private val _moviesList = MutableLiveData<List<Result>>(emptyList())
    val moviesList: LiveData<List<Result>> = _moviesList

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun loadMoviesModel() {
        viewModelScope.launch {
            _state.value = true

            _moviesList.value = interactor.getDataMovies()

            _state.value = false
        }
    }

}