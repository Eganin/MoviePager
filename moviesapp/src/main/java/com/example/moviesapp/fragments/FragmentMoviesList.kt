package com.example.moviesapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MoviesAdapter
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.domain.MoviesDataSource

class FragmentMoviesList : Fragment() {

    interface OnRecalculationScreen {
        fun recalculationScreen(): Int
    }

    private val adapter = MoviesAdapter()
    private var onRecalculationScreen: OnRecalculationScreen? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view = view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter.onClickPoster = context as MoviesAdapter.OnClickPoster
        onRecalculationScreen = context as OnRecalculationScreen

    }

    override fun onDetach() {
        super.onDetach()
        adapter.onClickPoster = null
        onRecalculationScreen = null
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.movies_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(
            requireContext(), onRecalculationScreen?.recalculationScreen()!!
        )

        recyclerView.adapter = adapter
        adapter.bindMovies(newMovies = MoviesDataSource().getMovies())
        adapter.notifyDataSetChanged()
    }
}