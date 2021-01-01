package com.example.moviesapp.routing

import com.example.moviesapp.pojo.movies.popular.Result


interface Router {

    fun openMoviesList()
    fun openMovieDetails(movie : Result)
}