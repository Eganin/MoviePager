package com.example.moviesapp.fragments.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.RetrofitModule
import com.example.moviesapp.pojo.movies.credits.ResponseCredits
import com.example.moviesapp.pojo.movies.details.ResponseMovieDetail
import kotlinx.coroutines.launch

class MoviesDetailsViewModel : ViewModel() {

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
            _info.value = RetrofitModule.apiMovies.getDetailInfo(movieId = id.toString())

            _credits.value = RetrofitModule.apiMovies.getCreditsMovie(movieId = id.toString())
        }
    }
}