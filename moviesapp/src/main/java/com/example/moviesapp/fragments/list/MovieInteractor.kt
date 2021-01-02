package com.example.moviesapp.fragments.list

import com.example.moviesapp.network.RetrofitModule
import com.example.moviesapp.pojo.movies.popular.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieInteractor(private val dispatcher: CoroutineDispatcher) {
    suspend fun getDataMovies(page : String): List<Result> = withContext(dispatcher) {
        RetrofitModule.apiMovies.getPopularMovies(page = page ).results
    }
}