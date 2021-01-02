package com.example.moviesapp.routing

import com.example.moviesapp.pojo.movies.details.ResponseMovieDetail


interface Router {

    fun openMoviesList()
    fun openMovieDetails(movie : ResponseMovieDetail)
}