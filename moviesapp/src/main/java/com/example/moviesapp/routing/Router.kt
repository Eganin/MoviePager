package com.example.moviesapp.routing

import com.example.moviesapp.data.models.Movie

interface Router {

    fun openMoviesList()
    fun openMovieDetails(movie : Movie)
}