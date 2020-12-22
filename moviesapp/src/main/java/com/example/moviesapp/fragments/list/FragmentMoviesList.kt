package com.example.moviesapp.fragments.list

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
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.data.models.loadMovies
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {

    private val adapter = MoviesAdapter()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var moviesData: List<Movie> = listOf()
    private var recycler: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        downloadData(view = view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesAdapter.OnClickPoster) adapter.onClickPoster = context
    }

    override fun onDetach() {
        super.onDetach()
        adapter.onClickPoster = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler?.adapter = null
        recycler = null
        uiScope.cancel()
    }

    private fun downloadData(view: View) {
        uiScope.launch {
            val differed = uiScope.async {
                moviesData = loadMovies(requireContext())
            }
            differed.await()
            setupRecyclerView(view = view, data = moviesData)
        }
    }

    private fun setupRecyclerView(view: View, data: List<Movie>) {
        recycler = view.findViewById(R.id.movies_recycler_view)
        recycler?.layoutManager = GridLayoutManager(
            requireContext(), recalculationScreen()
        )

        recycler?.adapter = adapter
        adapter.bindMovies(newMovies = data)
        adapter.notifyDataSetChanged()
    }

    private fun recalculationScreen(): Int {
        // вычесляем кол-во колонок для GridLayout
        val displayMetrics = DisplayMetrics()

        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 185 > 2) width / 185 else 2
    }
}