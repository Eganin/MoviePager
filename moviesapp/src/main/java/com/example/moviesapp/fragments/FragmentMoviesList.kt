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
import com.example.moviesapp.adapters.MoviesAdapter
import org.json.JSONObject

class FragmentMoviesList : Fragment() {

    private val adapter = MoviesAdapter()

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
        if (context is MoviesAdapter.OnClickPoster) adapter.onClickPoster = context

    }

    override fun onDetach() {
        super.onDetach()
        adapter.onClickPoster = null

    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.movies_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(
            requireContext(), recalculationScreen() ?: DEFAULT_COLUMN
        )

        recyclerView.adapter = adapter
        adapter.bindMovies(newMovies = MoviesDataSource().getMovies())
        adapter.notifyDataSetChanged()
    }

    private fun recalculationScreen(): Int {
        // вычесляем кол-во колонок для GridLayout
        val displayMetrics = DisplayMetrics()

        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 185 > 2) width / 185 else 2
    }

    companion object {
        private const val DEFAULT_COLUMN = 2
    }
}