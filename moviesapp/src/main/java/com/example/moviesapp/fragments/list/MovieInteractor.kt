package com.example.moviesapp.fragments.list

import android.content.Context
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.data.models.loadMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieInteractor(private val dispatcher : CoroutineDispatcher , private val context: Context) {
    suspend fun getDataMovies() : List<Movie> = withContext(dispatcher){
        loadMovies(context = context)
    }
}