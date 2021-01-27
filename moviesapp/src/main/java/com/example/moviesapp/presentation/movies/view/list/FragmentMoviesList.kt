package com.example.moviesapp.presentation.movies.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.viewpager.widget.ViewPager
import com.example.moviesapp.R


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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager = null
    }

}