package com.example.moviesapp.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoviesDetailsViewModel : ViewModel() {

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun startLoadingData() {
        _state.value = true
    }

    fun stopLoadingData() {
        _state.value = false
    }
}