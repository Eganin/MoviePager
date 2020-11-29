package com.example.moviesapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MovieAdapter
import java.util.*

class FragmentMoviesList : Fragment() {

    private val adapter = MovieAdapter(mutableListOf(1))

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
        if (context is MovieAdapter.OnClickPoster)
            adapter.onClickPoster = context

    }

    override fun onDetach() {
        super.onDetach()
        adapter.onClickPoster = null
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.movies_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(
            requireContext(), arguments?.getInt(COLUMN_COUNT_SAVE) ?: DEFAULT_COLUMN_COUNT
        )
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(name: String, columnCount: Int): FragmentMoviesList {
            val args = Bundle()
            args.putString(NAME_SAVE, name)
            args.putInt(COLUMN_COUNT_SAVE, columnCount)
            val fragment = FragmentMoviesList()
            fragment.arguments = args
            return fragment
        }

        private const val NAME_SAVE = "name"
        private const val COLUMN_COUNT_SAVE = "countColumn"
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}