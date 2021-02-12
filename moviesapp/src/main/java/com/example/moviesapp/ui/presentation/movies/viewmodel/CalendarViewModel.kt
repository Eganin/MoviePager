package com.example.moviesapp.ui.presentation.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.model.entities.movies.details.ResponseMovieDetail


class CalendarViewModel : ViewModel(){

    private val _movie = MutableLiveData<ResponseMovieDetail>()
    val movie: LiveData<ResponseMovieDetail> = _movie

    fun startView(data : ResponseMovieDetail){
        _movie.value =data
    }


    @Suppress("UNCHECKED_CAST")
    class Factory :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CalendarViewModel() as T
        }
    }
}