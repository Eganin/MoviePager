package com.example.moviesapp.screens.movies

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesapp.R
import com.example.moviesapp.application.MovieApp
import com.example.moviesapp.presentation.movies.utils.routing.Router
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.presentation.movies.view.details.FragmentMoviesDetails
import com.example.moviesapp.presentation.movies.view.list.FragmentMoviesList
import com.example.moviesapp.presentation.movies.view.list.MoviesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Router, MoviesAdapter.OnClickPoster {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openMoviesList()
            intent?.let(::handlerIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let(::handlerIntent)
    }

    override fun createMoviesDetailsFragment(
        movieId: Long,
        configuration: Images?
    ) {
        openMovieDetails(movieId = movieId, configuration = configuration)
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

    override fun openMoviesList() {
        openNewFragment(fragment = FragmentMoviesList(), addToBackStack = false)
    }

    override fun openMovieDetails(movieId: Long, configuration: Images?) {
        openNewFragment(
            fragment = FragmentMoviesDetails.newInstance(
                movieId = movieId,
                configuration = configuration
            ),
            addToBackStack = true
        )
    }

    private fun handlerIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toLongOrNull()
                scope.launch {
                    val configuration = (applicationContext as MovieApp).myComponent
                        .getMovieRepository().getConfiguration()
                    id?.let { openMovieDetails(movieId = it, configuration = configuration) }
                }
            }
        }
    }

    private fun openNewFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment)

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        }

        fragmentTransaction.commit()
    }

}