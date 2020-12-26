package com.example.moviesapp.fragments.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.data.models.loadMovies
import kotlinx.coroutines.launch

class MoviesListViewModel(private val interactor: MovieInteractor) :
    ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>(emptyList())
    val moviesList: LiveData<List<Movie>> = _moviesList

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