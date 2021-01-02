package com.example.moviesapp.routing

import com.example.moviesapp.pojo.configuration.Images


interface Router {

    fun openMoviesList()
    fun openMovieDetails(movieId : Long , configuration : Images?)
}