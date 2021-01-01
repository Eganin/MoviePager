package com.example.moviesapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.fragments.details.MoviesDetailsViewModel
import com.example.moviesapp.fragments.list.MovieInteractor
import com.example.moviesapp.fragments.list.MoviesListViewModel
import kotlinx.coroutines.Dispatchers

class ViewModelsFactory(val page : String? = null ) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(
            interactor = MovieInteractor(
                dispatcher = Dispatchers.IO,
                page=page
            )
        )
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}