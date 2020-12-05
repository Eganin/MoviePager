package com.example.moviesapp.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MoviesAdapter
import com.example.moviesapp.fragments.FragmentMoviesDetails
import com.example.moviesapp.fragments.FragmentMoviesList

class MainActivity : AppCompatActivity(), MoviesAdapter.OnClickPoster {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.main_container,
                    FragmentMoviesList.newInstance(columnCount = getColumnCount())
                )
                addToBackStack(null)
                commit()
            }
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
            replace(R.id.main_container, FragmentMoviesDetails())
            addToBackStack(null)
            commit()
        }


    }


}