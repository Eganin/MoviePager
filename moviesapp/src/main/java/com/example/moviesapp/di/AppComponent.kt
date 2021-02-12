package com.example.moviesapp.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.model.repositories.MovieRepository
import com.example.moviesapp.model.repositories.WorkerRepository
import com.example.moviesapp.presentation.movies.viewmodel.MoviesDetailsViewModel
import com.example.moviesapp.presentation.movies.viewmodel.MoviesListViewModel
import com.example.moviesapp.ui.presentation.movies.viewmodel.CalendarViewModel
import java.util.*

class AppComponent(context: Context) {

    private val movieRepository =
        MovieRepository(applicationContext = context, language = Locale.getDefault().language)
    private val workerRepository = WorkerRepository()

    fun getMoviesViewModel(fragment: Fragment): MoviesListViewModel {
        return ViewModelProvider(fragment, MoviesListViewModel.Factory(movieRepository)).get(
            MoviesListViewModel::class.java
        )
    }


    fun getMoviesDetailsViewModel(fragment: Fragment): MoviesDetailsViewModel {
        return ViewModelProvider(fragment, MoviesDetailsViewModel.Factory(movieRepository)).get(
            MoviesDetailsViewModel::class.java
        )
    }

    fun getCalendarViewModel(fragment : Fragment) : CalendarViewModel {
        return ViewModelProvider(fragment, CalendarViewModel.Factory()).get(
            CalendarViewModel::class.java
        )
    }

    fun getMovieRepository() = movieRepository

    fun getWorkerRepository() = workerRepository

}