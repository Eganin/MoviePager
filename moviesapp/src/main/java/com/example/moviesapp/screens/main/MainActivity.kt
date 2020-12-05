package com.example.moviesapp.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MoviesAdapter
import com.example.moviesapp.fragments.FragmentMoviesDetails
import com.example.moviesapp.fragments.FragmentMoviesList

class MainActivity : AppCompatActivity(), MoviesAdapter.OnClickPoster , FragmentMoviesList.OnRecalculationScreen {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.main_container,
                    FragmentMoviesList()
                )
                addToBackStack(null)
                commit()
            }
        }

    }

    override fun click(position: Int) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, FragmentMoviesDetails())
            addToBackStack(null)
            commit()
        }


    }

    override fun recalculationScreen() : Int {
        // вычесляем кол-во колонок для GridLayout
        val displayMetrics = DisplayMetrics()

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 185 > 2) width / 185 else 2
    }


}