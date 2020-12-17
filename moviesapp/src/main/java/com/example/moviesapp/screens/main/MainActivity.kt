package com.example.moviesapp.screens.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MoviesAdapter
import com.example.moviesapp.fragments.FragmentMoviesDetails
import com.example.moviesapp.fragments.FragmentMoviesList

class MainActivity : AppCompatActivity(), MoviesAdapter.OnClickPoster{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_container, FragmentMoviesList())
                commit()
            }
        }
    }

    override fun createMoviesDetailsFragment(position: Int) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, FragmentMoviesDetails())
            addToBackStack(null)
            commit()
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }

}