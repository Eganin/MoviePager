package com.example.moviesapp.presentation.movies.utils.routing

import com.example.moviesapp.model.entities.configuration.Images

interface Router {

    fun openMoviesList()
    fun openMovieDetails(movieId : Long , configuration : Images?)
}