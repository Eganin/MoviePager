package com.example.moviesapp.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MoviesAdapter
import com.example.moviesapp.fragments.details.FragmentMoviesDetails
import com.example.moviesapp.fragments.list.FragmentMoviesList
import com.example.moviesapp.pojo.movies.popular.Result
import com.example.moviesapp.routing.Router

class MainActivity : AppCompatActivity(), Router, MoviesAdapter.OnClickPoster {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            openMoviesList()
        }
    }

    override fun createMoviesDetailsFragment(movie: Result) {
        openMovieDetails(movie = movie)
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

    override fun openMoviesList() {
        openNewFragment(fragment = FragmentMoviesList(), addToBackStack = false)
    }

    override fun openMovieDetails(movie: Result) {
        openNewFragment(
            fragment = FragmentMoviesDetails.newInstance(movie = movie),
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