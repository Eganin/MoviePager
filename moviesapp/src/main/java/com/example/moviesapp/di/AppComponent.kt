package com.example.moviesapp.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.model.repositories.MovieRepository
import com.example.moviesapp.presentation.movies.viewmodel.MoviesDetailsViewModel
import com.example.moviesapp.presentation.movies.viewmodel.MoviesListViewModel

class AppComponent(context: Context) {

    private val repository = MovieRepository(applicationContext = context)

    fun getMoviesViewModel(fragment: Fragment): MoviesListViewModel {
        return ViewModelProvider(fragment, MoviesListViewModel.Factory(repository)).get(
            MoviesListViewModel::class.java
        )
    }

    fun getMoviesDetailsViewModel(fragment: Fragment) : MoviesDetailsViewModel {
        return ViewModelProvider(fragment, MoviesDetailsViewModel.Factory(repository)).get(
            MoviesDetailsViewModel::class.java
        )
    }

}