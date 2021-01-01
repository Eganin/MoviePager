package com.example.moviesapp.application

import android.app.Application
import android.util.Log
import com.example.moviesapp.network.RetrofitModule
import com.example.moviesapp.pojo.configuration.GenreList
import com.example.moviesapp.pojo.configuration.Images
import kotlinx.coroutines.*

class MovieApp : Application() {
    val TAG = MovieApp::class.java.simpleName
    private val dispatcher = Dispatchers.IO
    lateinit  var configuration : Images
    lateinit var genreList: GenreList

    fun createCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG , throwable.message ?: "invalid message")
        createCoroutineScope()
    }


    private  suspend fun getConfiguration() : Images = withContext(dispatcher){
        RetrofitModule.apiMovies.getConfiguration().images
    }

    private suspend fun getGenres() : GenreList = withContext(dispatcher){
        RetrofitModule.apiMovies.getGenres()
    }

    override fun onCreate() {
        super.onCreate()
        createCoroutineScope().launch (exceptionHandler) {
            configuration = getConfiguration()
            genreList = getGenres()
        }
    }
}