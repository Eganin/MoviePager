package com.example.moviesapp.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MovieAdapter
import com.example.moviesapp.fragments.FragmentMoviesDetails
import com.example.moviesapp.fragments.FragmentMoviesList

class MainActivity : AppCompatActivity(), MovieAdapter.OnClickPoster {

    private lateinit var fragmentMoviesDetails: FragmentMoviesDetails
    private lateinit var fragmentMoviesList: FragmentMoviesList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentMoviesDetails = FragmentMoviesDetails.newInstance("avengers")
        fragmentMoviesList =
            FragmentMoviesList.newInstance(name = "mainList", columnCount = getColumnCount())
        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_container, fragmentMoviesList)
            commit()
        }

    }

    private fun getColumnCount(): Int {
        // вычесляем кол-во колонок для GridLayout
        val displayMetrics = DisplayMetrics()

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 185 > 2) width / 185 else 2
    }

    override fun click(position: Int) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, fragmentMoviesDetails)
            commit()
        }


    }


}