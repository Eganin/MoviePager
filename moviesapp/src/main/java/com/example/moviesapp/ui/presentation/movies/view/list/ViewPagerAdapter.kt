package com.example.moviesapp.presentation.movies.view.list

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moviesapp.ui.presentation.movies.view.list.FragmentPager


class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {


    override fun getCount(): Int = 5

    override fun getItem(position: Int)= when (position) {
        0 -> FragmentPager.newInstance(text = "Popular")
        1 -> FragmentPager.newInstance(text = "Top rated")
        2 -> FragmentPager.newInstance(text = "Now playong")
        3 -> FragmentPager.newInstance(text = "Up coming")
        else -> FragmentPager.newInstance(text = "Search")
    }

}