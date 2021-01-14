package com.example.moviesapp.screens.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesapp.R
import com.example.moviesapp.presentation.movies.utils.routing.Router
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.presentation.movies.view.details.FragmentMoviesDetails
import com.example.moviesapp.presentation.movies.view.list.FragmentMoviesList
import com.example.moviesapp.presentation.movies.view.list.MoviesAdapter

class MainActivity : AppCompatActivity(), Router, MoviesAdapter.OnClickPoster {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openMoviesList()
        }
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

    private fun openNewFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val fragmentTransaction =
                supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment)

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        }

        fragmentTransaction.commit()
    }

}