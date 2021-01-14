package com.example.moviesapp.presentation.movies.view.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.viewpager.widget.ViewPager
import com.example.moviesapp.R
import com.example.moviesapp.presentation.movies.viewmodel.Counter


class FragmentMoviesList : Fragment() {

    private var viewPager: ViewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.pager, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.view_pager)
        viewPager?.offscreenPageLimit = 1
        viewPager?.adapter= ViewPagerAdapter(childFragmentManager)

        viewPager?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d("position",position.toString())
            }

            override fun onPageSelected(position: Int) {
                viewPager?.currentItem = position
                Counter.count =0
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.d("state",state.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager = null
    }

}